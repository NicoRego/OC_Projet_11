package com.nicorego.NHSEmergency.repository;

import com.nicorego.NHSEmergency.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Hospital, Long> {

}
