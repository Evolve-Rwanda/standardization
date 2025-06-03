package com.example.springauth.models.jpa;

import jakarta.persistence.Column;

public class OversightInstitutionAdmin {

    @Column(length = 50)
    private String lastName;

    @Column(length = 100)
    private String position;

    @Column(length = 50)
    private String workEmail;

    @Column(length = 30)
    private String phoneNumber;

    @Column(length = 255)
    private String password;

}
