package com.nicorego.NHSEmergency.repository;

import com.nicorego.NHSEmergency.model.Hospital;
import com.nicorego.NHSEmergency.model.Specialty;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    List<Hospital> findBySpecialtiesContaining(String specialties);
}
