package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class TenderPublicationNotice {

    @Id
    @Column(length = 50)
    private String id;

    @Column(columnDefinition = "TEXT")
    private String tenderTitle;

    @Column(length = 50)
    private String tenderReferenceNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 30)
    private String procurementType;

    @Column(length = 30)
    private String procurementMethod;

    @Column(columnDefinition = "DATE")
    private String bidSubmissionStartDate;

    @Column(columnDefinition = "DATE")
    private String bidSubmissionDeadline;

    private int numberOfLots;
}
