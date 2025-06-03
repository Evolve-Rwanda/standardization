package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class ContractingAuthorityDepartment {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 255)
    private String name;

    @Column(length = 255)
    private String managerName; // 1:*

    @Column(length = 255)
    private String managerEmail; // 1:*

    @Column(length = 255)
    private String branch; // requires further normalization

}
