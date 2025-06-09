package com.example.springauth.repositories;

import com.example.springauth.models.jpa.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SentEmailRepository extends JpaRepository<Email, String> {
}
