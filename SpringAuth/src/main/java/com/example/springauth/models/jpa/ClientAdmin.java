package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClientAdmin {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    private String firstName;

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

    public ClientAdmin() {
    }
}
