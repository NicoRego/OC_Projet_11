package com.nicorego.NHSEmergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicorego.NHSEmergency.model.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

}


