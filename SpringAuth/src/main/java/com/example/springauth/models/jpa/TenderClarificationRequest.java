package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class TenderClarificationRequest {

    @Id
    @Column(length = 50)
    private String requisitionReferenceNumber;

    @Column(length = 50)
    private String tenderReferenceNumber;

    @Column(columnDefinition = "DATE")
    private String requestDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String economicOperatorCode;

    @Column(columnDefinition = "DATE")
    private String responseDate;

    @Column(length = 10)
    private String status;

}
