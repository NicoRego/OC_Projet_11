package com.nicorego.NHSEmergency.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.nicorego.NHSEmergency.singleton.EntityManagerFactorySingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nicorego.NHSEmergency.exceptions.BadRequest;

import com.nicorego.NHSEmergency.model.Hospital;
import com.nicorego.NHSEmergency.repository.HospitalRepository;

import lombok.Data;

import static com.nicorego.NHSEmergency.GeolocationUtils.distanceHaversine;

@Data
@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    private Hospital nearestHospital;

    public Hospital getNearestHospital(Double latitude, Double longitude, Integer specialtyId) {

        // List for distance and free beds
        ArrayList<Double> distanceHospitals = new ArrayList<>();

        // Nearest hospital
        Hospital nearestHospital;

        // Get hospitals by available beds
        List<Hospital> freeBedsHospitals = findByFreeBeds();

        // Filter the hospitals by specialty and free beds
        List<Hospital> filteredHospitals = freeBedsHospitals.stream()
                .filter(h -> h.getSpecialties().contains(specialtyId))
                .collect(Collectors.toList());

        // Get distance from latitude and longitude for each hospital in list
        for (Hospital filteredHospital : filteredHospitals) {
            double dist = distanceHaversine(latitude, longitude, filteredHospital.getLatitude(), filteredHospital.getLongitude());
            distanceHospitals.add(dist);
        }

        // Find the index of the nearest hospital
        nearestHospital = filteredHospitals.get(distanceHospitals.indexOf(Collections.min(distanceHospitals)));

        return nearestHospital;
    }

    public List<Hospital> findByFreeBeds() {

        try (EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager()) {
            String query = "SELECT * FROM hospital WHERE hospital.free_beds > 0";
            TypedQuery<Hospital> typedQuery = em.createQuery(query, Hospital.class);
            return typedQuery.getResultList();
        }
    }

    public Hospital bookRoom(Hospital bookedHospital) throws BadRequest {
        Hospital updatedHospital = null;

        if (bookedHospital.getFreeBeds() > 0) {
            updatedHospital = bookedHospital;
            int freeBeds = updatedHospital.getFreeBeds() - 1;
            updatedHospital.setFreeBeds(freeBeds);

            try (EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager()) {
                em.getTransaction().begin();
                em.merge(updatedHospital);
                em.getTransaction().commit();
            }
        }
        return updatedHospital;
    }

    public Hospital getHospitalById(Integer hospitalId) {
        try (EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager()) {
            return em.find(Hospital.class, hospitalId);
        }
    }
}