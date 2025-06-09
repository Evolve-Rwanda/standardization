package com.example.springauth.repositories;

import com.example.springauth.models.jpa.SentSMS;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentSMSRepository extends JpaRepository<SentSMS, String> {
}
