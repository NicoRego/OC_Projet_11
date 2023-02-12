package com.nicorego.NHSEmergency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
   List<Hospital> findBySpecialtiesContaining(String specialty);
}
