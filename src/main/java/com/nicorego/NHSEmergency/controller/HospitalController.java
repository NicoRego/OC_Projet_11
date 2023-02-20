package com.nicorego.NHSEmergency.controller;

import com.nicorego.NHSEmergency.exceptions.BadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nicorego.NHSEmergency.model.Hospital;
import com.nicorego.NHSEmergency.service.HospitalService;

@RestController
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @RequestMapping("/hospitals")

    @GetMapping("/search/nearest")
    public ResponseEntity<Hospital> getNearestHospital(@RequestParam("latitude") Double latitude,
                                                        @RequestParam("longitude") Double longitude,
                                                        @RequestParam("specialty") Integer specialtyId) {
        Hospital nearestHospital = hospitalService.getNearestHospital(latitude, longitude, specialtyId);
        if (nearestHospital == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(nearestHospital);
        }
    }

    @PutMapping("/room/book")
    public ResponseEntity<Hospital> bookRoom(@RequestParam Integer hospitalId) {
        Hospital bookedHospital = hospitalService.getHospitalById(hospitalId);
        try {
            Hospital updatedHospital = hospitalService.bookRoom(bookedHospital);
            return ResponseEntity.ok(updatedHospital);
        } catch (BadRequest e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

