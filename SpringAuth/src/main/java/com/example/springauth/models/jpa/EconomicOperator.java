package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class EconomicOperator {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 255)
    private String companyName;

    @Column(length = 150)
    private String businessType;

    @Column(length = 150)
    private String procurementTypeOfInterest;

    @Column(length = 255)
    private String logo;

    @Column(length = 30)
    private String taxIdentificationNumber;

    @Column(length = 30)
    private String employerSocialSecurityNumber;

    @Column(length = 100)
    private String businessCategory;

    @Column(length = 100)
    private String unspcCategoryOfInterest;

    @Column(length = 100)
    private String email;

    @Column(length = 30)
    private String phoneNumber;

    @Column(length = 100)
    private String officeCountry;

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
    private String streetAddress;

    @Column(length = 120)
    private String website;

    @Column(length = 150)
    private String bankName;

    @Column(length = 50)
    private String bankAccount;

    @Column(length = 100)
    private String accountHolder;

    @Column(length = 50)
    private String bankCurrency;

    @Column(length = 100)
    private String numberOfEmployees; // String to easily switch from exact count to a range x - y, x1 - y1, ...

    @Column(length = 50)
    private String operationStartDate; // perhaps this works better than years-in-operation in the user stories document.

    // EO beneficial ownership information (Multiplicity unknown, this may become a standalone model.
    @Column(length = 255)
    private String legalName;

    @Column(length = 100)
    private String nationality;

    @Column(length = 100)
    private String ownershipNature;

    @Column(length = 10)
    private String ownershipPercentage;

    @Column(length = 10)
    private String supportingDocuments; // to be factored out. It is a *:*

    // representative
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
