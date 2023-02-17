package com.nicorego.NHSEmergency.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "specialty")
public class Specialty {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "specialties")
    private Set<Hospital> hospitals;

    @Column(name="name")
    private String name;

}
