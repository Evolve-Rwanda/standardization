package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/*
*  The procurement roles for the client will include
*  - Procurement Auditor
*  - Monitoring Officer
*  - Investigation
*  - Debarment officer
*  - Chief Budget Manager
*  - Catalogue officer
*  - Complaints Review Board
*  - etc
*  These will have to be added through a form for the other actors in the system
* */

@Entity
public class ProcurementRole {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
