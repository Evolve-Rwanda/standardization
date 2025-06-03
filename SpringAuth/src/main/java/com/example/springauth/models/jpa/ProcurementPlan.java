package com.example.springauth.models.jpa;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;



@Entity
public class ProcurementPlan {

    @Id
    @Column(length = 50)
    private String procurementPlanNumber;

    @Column(columnDefinition = "TEXT")
    private String tenderTitle;

    @Column(length = 30)
    private String procurementType;

    @Column(length = 30)
    private String procurementMethod;

    @Column(length = 100)
    private String itemCode;

    @Column(columnDefinition = "TEXT")
    private String itemDescription;

    @Column(length = 100)
    private String unitOfMeasure;

    @Column(length = 300)
    private String quantity;

    @Column(length = 100)
    private String sourceOfFunds;

    @Column(length = 300)
    private String donorName; // isn't linking to an ID much better if the donor must be registered?

    @Column(columnDefinition = "TEXT")
    private String budgetLine; // this is multivalue optional, set it out to be an independent table

    @Column(length = 100)
    private String executionPeriod;

    @Column(length = 20)
    private String contractExists;

    private float years;
    private double estimatedAmount;

    @Column(length = 30)
    private String currency;

    @Column(length = 100)
    private String schemes; // perhaps multivalued and should be normalized further

    @Column(columnDefinition = "DATE")
    private String tenderPreparationDate;

    @Column(columnDefinition = "DATE")
    private String tenderPublicationDate;

    @Column(columnDefinition = "DATE")
    private String bidOpeningDate;

    @Column(columnDefinition = "DATE")
    private String bidEvaluationDate;

    @Column(columnDefinition = "DATE")
    private String contractAwardDate;

    @Column(columnDefinition = "DATE")
    private String contractSigningDate;

    @Column(columnDefinition = "DATE")
    private String contractStartDate;

    @Column(columnDefinition = "DATE")
    private String contractClosingDate;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(length = 20)
    private String wereAnyPrerequisiteActivitiesCarriedOut;

    @Column(length = 25)
    private String status;

    @Column(length = 100)
    private String procurementStatus;

}
