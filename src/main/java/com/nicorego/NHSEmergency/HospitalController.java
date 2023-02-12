package com.nicorego.NHSEmergency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.nicorego.NHSEmergency.GeolocationUtils.distance;

@RestController
public class HospitalController {
    @Autowired
    private HospitalRepository hospitalRepository;

    @GetMapping("/hospitals/nearest")
    public List<Hospital> getNearestHospitals(@RequestParam("latitude") Double latitude,
                                              @RequestParam("longitude") Double longitude,
                                              @RequestParam("specialty") String specialty) {
        List<Hospital> hospitals = hospitalRepository.findBySpecialtiesContaining(specialty);

        hospitals.sort((h1, h2) -> {
            double dist1 = distance(latitude, longitude, h1.getLatitude(), h1.getLongitude());
            double dist2 = distance(latitude, longitude, h2.getLatitude(), h2.getLongitude());
            return Double.compare(dist1, dist2);
        });

        return hospitals;
    }
}
