package com.nicorego.NHSEmergency.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="latitude")
    private Double latitude;
    
    @Column(name="longitude")
    private Double longitude;

    @Column(name="free_beds")
    private Integer freeBeds;

    @ManyToMany
    @JoinTable(name = "specialty",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Integer> specialties;

    // getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getFreeBeds() {
        return freeBeds;
    }

    public List<Integer> getSpecialties() {
        return specialties;
    }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setFreeBeds(Integer freeBeds) {
        this.freeBeds = freeBeds;
    }

    public void setSpecialties(List<Integer> specialties) {
        this.specialties = specialties;
    }

}
