package com.nicorego.NHSEmergency.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Hospital> getHospital(final Long id) {
        return hospitalRepository.findById(id);
    }
    
    public Iterable<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }

    public void deleteHospital(final Long id) {
        hospitalRepository.deleteById(id);
    }
    
    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Hospital getNearestHospital(Double latitude, Double longitude, Integer specialtyId) {

        // List for distance and free beds
        ArrayList<Double> distanceHospitals = new ArrayList<>();

        // Nearest hospital
        Hospital nearestHospital;

        // Get hospitals by specialty
        List<Hospital> specialtyHospitals = findBySpecialtiesContaining(specialtyId);

        // Get available beds in previous hospital list
        List<Hospital> freeBedsHospitals = findByFreeBeds();

        // Get distance from latitude and longitude for each hospital in list
        for (Hospital freeBedsHospital : freeBedsHospitals) {
            double dist = distanceHaversine(latitude, longitude, freeBedsHospital.getLatitude(), freeBedsHospital.getLongitude());
            distanceHospitals.add(dist);
        }

        // Find the index of the nearest hospital
        int index = distanceHospitals.indexOf(Collections.min(distanceHospitals));
        nearestHospital = freeBedsHospitals.get(index);

        return nearestHospital;
    }

    public List<Hospital> getAvailableBeds(Double latitude, Double longitude, Integer specialtyId) {
        Hospital nearestHospital = getNearestHospital(latitude, longitude, specialtyId);
        List<Hospital> freeBedsHospitals = findByFreeBeds();

        // Filter the hospitals by specialty and free beds
        List<Hospital> filteredHospitals = freeBedsHospitals.stream()
                .filter(h -> h.getSpecialties().contains(specialtyId))
                .filter(h -> h.getId() != nearestHospital.getId())
                .collect(Collectors.toList());

        return filteredHospitals;
    }

    public List<Hospital> findBySpecialtiesContaining(Integer specialtyId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit-name");
        EntityManager em = emf.createEntityManager();

        try {
            String query =
                    """
                            SELECT *
                            FROM hospital
                            INNER JOIN hospitals_specialties ON hospital.id = hospitals_specialties.hospital_id
                            WHERE hospitals_specialties.specialty_id = specialty.id""";
            TypedQuery<Hospital> typedQuery = em.createQuery(query, Hospital.class);
            typedQuery.setParameter("specialtyId", "%" + specialtyId + "%");

            return typedQuery.getResultList();
        } finally {
            em.close();
            emf.close();
        }
    }

    public List<Hospital> findByFreeBeds() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit-name");
        EntityManager em = emf.createEntityManager();

        try {
            String query = "SELECT * FROM hospital WHERE free_beds > 0";
            TypedQuery<Hospital> typedQuery = em.createQuery(query, Hospital.class);
            return typedQuery.getResultList();
        } finally {
            em.close();
            emf.close();
        }
    }
}