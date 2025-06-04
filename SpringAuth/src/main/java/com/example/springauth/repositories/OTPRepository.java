package com.example.springauth.repositories;

import com.example.springauth.models.jpa.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OTPRepository extends JpaRepository<OTP, String> {
}
