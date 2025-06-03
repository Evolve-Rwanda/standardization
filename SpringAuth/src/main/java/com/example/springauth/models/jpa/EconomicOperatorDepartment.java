package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class EconomicOperatorDepartment {

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
