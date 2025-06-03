package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Lot {

    @Id
    @Column(length = 50)
    private String id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(length = 50)
    private String type;

    private double estimatedCost;

    @Column(length = 100)
    private String scheme;

}
