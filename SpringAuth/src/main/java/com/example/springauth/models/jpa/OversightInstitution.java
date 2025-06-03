package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OversightInstitution {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 255)
    private String institutionName;

    @Column(length = 100)
    private String abbreviationName;

    @Column(length = 30)
    private String taxIdentificationNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 30)
    private String phoneNumber;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String province;

    @Column(length = 100)
    private String district;

    @Column(length = 100)
    private String sector;

    @Column(length = 100)
    private String cell;

    @Column(length = 100)
    private String village;

    @Column(length = 100)
    private String currency;

    @Column(length = 100)
    private String timezone;

    @Column(length = 120)
    private String website;

    @Column(length = 100)
    private String representativeName;

    @Column(length = 10)
    private String representativeGender;

    @Column(length = 100)
    private String representativeEmail;

    @Column(length = 100)
    private String representativeNID;

    @Column(length = 100)
    private String representativePosition;
}
