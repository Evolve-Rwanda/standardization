package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class EvaluationCriterion {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 150)
    private String name;

    @Column(length = 50)
    private String phase;

    @Column(columnDefinition = "TEXT")
    private String description;

    private float scoreWeight;

    @Column(length = 150)
    private String requiredDocuments; // requires further normalization

}
