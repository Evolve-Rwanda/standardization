package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class SiteVisit {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    private String tenderReferenceNumber;

    private int visitNumber;

    @Column(columnDefinition = "TIMESTAMP")
    private String dateAndTime;

    @Column(columnDefinition = "TEXT")
    private String purposeOfTheVisit;

    // connected to the visit attendee entity, *:*


}
