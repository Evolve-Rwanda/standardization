package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class EconomicOperatorMember {

    // all member objects to be mapped to their respective entities - 1:*
    // providing the ID of their respective entity will be key to signing up
    @Id
    @Column(length = 50)
    private String id;

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
