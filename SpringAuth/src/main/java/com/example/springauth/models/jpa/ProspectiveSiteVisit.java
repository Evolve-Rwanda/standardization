package com.example.springauth.models.jpa;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class ProspectiveSiteVisit {

    @Id
    @Column(length = 50)
    private String id;

    @Column(columnDefinition = "TIMESTAMP")
    private String visitDateAndTime;

    @Column(columnDefinition = "TEXT")
    private String meetingPoint;

    @Column(columnDefinition = "TEXT")
    private String visitInstructions;
}
