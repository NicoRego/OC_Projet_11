package com.nicorego.NHSEmergency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nicorego.NHSEmergency.model.Hospital;
import com.nicorego.NHSEmergency.service.HospitalService;

@RestController
public class HospitalController {
    
	@Autowired
	private HospitalService hospitalService;
		  
    @GetMapping("/hospital/nearest")
    public ResponseEntity<Hospital> getNearestHospitals(@RequestParam("latitude") Double latitude,
                                                        @RequestParam("longitude") Double longitude,
                                                        @RequestParam("specialty") Integer specialtyId) {

        Hospital nearestHospital = hospitalService.getNearestHospital(latitude, longitude, specialtyId);

        if (nearestHospital != null) {
            return ResponseEntity.ok(nearestHospital);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hospital/available")
    public ResponseEntity<Hospital> getAvailableBeds(@RequestParam("latitude") Double latitude,
                                                        @RequestParam("longitude") Double longitude,
                                                        @RequestParam("specialty") Integer specialtyId) {

        Hospital availableBeds = (Hospital) hospitalService.getAvailableBeds(latitude, longitude, specialtyId);

        if (availableBeds != null) {
            return ResponseEntity.ok(availableBeds);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

