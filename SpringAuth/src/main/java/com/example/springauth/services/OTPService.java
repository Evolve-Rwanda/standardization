package com.example.springauth.services;

import com.example.springauth.models.jpa.OTP;
import com.example.springauth.repositories.OTPRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OTPService {

    @Autowired
    OTPRepository otpRepository;

    @Transactional
    public OTP saveOTP(OTP otp){
        return otpRepository.save(otp);
    }

    @Transactional
    public OTP getOTPById(String id) {
        Optional<OTP> optional = otpRepository.findById(id);
        return optional.orElse(null);
    }
}
