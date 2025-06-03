package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class FiscalYear {

    @Id
    @Column(length = 50)
    private String fiscalYearId;

    @Column(columnDefinition = "DATE")
    private String startDate;

    @Column(columnDefinition = "DATE")
    private String endDate;

}
