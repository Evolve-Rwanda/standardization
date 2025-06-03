package com.example.springauth.models.jpa;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Tender {


    @Id
    @Column(length = 50)
    private String requisitionReferenceNumber;

    @Column(columnDefinition = "TEXT")
    private String tenderTitle;

    @Column(length = 50)
    private String tenderReferenceNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String contractingAuthority;

    @Column(length = 30)
    private String procurementType;

    @Column(length = 30)
    private String procurementMethod;

    @Column(length = 30)
    private String stage;

    @Column(length = 100)
    private String scheme;

    @Column(length = 30)
    private String items; // multivalued and has to be normalized.

    private double budgetAmount;
    private double tenderFee;

    @Column(length = 30)
    private String marketScope;

    @Column(length = 50)
    private String supplierCategory;

    @Column(length = 10)
    private String bidSecurityRequirement;

    @Column(length = 100)
    private String bidSecurityType;

    private float bidSecurityPercentage;

    @Column(length = 30)
    private String bidSecurityCurrency;

    @Column(scale = 120)
    private int bidValidityPeriod;

    @Column(length = 100)
    private String executionPeriod;

    @Column(length = 10)
    private String isFramework;

    @Column(columnDefinition = "DATE")
    private String preparationDate;

    @Column(columnDefinition = "DATE")
    private String publicationDate;

    @Column(columnDefinition = "DATE")
    private String clarificationDateDeadline;

    @Column(columnDefinition = "DATE")
    private String bidSubmissionStartDate;

    @Column(columnDefinition = "DATE")
    private String bidSubmissionDeadline;

    @Column(columnDefinition = "DATE")
    private String evaluationStartDate;

    @Column(columnDefinition = "DATE")
    private String evaluationEndDate;

    @Column(columnDefinition = "TIMESTAMP")
    private String bidOpeningDateAndTime;

    @Column(length = 10)
    private String allowsJointVenture;

    @Column(length = 10)
    private String allowsSubContracting;

    private int maximumJointVenturePartners;
    private float subcontractingScope;
    private int numberOfLots;

    @Column(length = 30)
    private String lotBiddingEligibility;

    @Column(length = 300)
    private String placeOfDelivery;

    @Column(length = 30)
    private String timeOfDelivery;

    @Column(columnDefinition = "TEXT")
    private String termsOfReference;

    @Column(length = 10)
    private String siteVisit; // if yes, the site visit is also further normalized for this

    @Column(length = 20)
    private String tenderStatus;

    @Column(length = 30)
    private String selectionMethod;

    @Column(length = 50)
    private String evaluationCriteria;

    @Column(length = 30)
    private String environmentCriteria;

    @Column(length = 100)
    private String languageOfSubmission;

    private String preBidMeeting; // requires further normalization

    @Column(columnDefinition = "TEXT")
    private String requiredDocuments;

    @Column(length = 100)
    private String documentTypes;

    @Column(length = 255)
    private String attachments;

    @Column(length = 100)
    private String submissionStatus; // save, submitted for review, pending review

}
