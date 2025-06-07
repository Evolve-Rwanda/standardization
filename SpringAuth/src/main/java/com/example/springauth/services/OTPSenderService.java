package com.example.springauth.services;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OTPSenderService {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.service_sid}")
    private String serviceSid;


    public void sendOtp(String phoneNumber) {
        Twilio.init(accountSid, authToken);
        VerificationCreator verificationCreator = Verification.creator(serviceSid, phoneNumber, "sms");
        verificationCreator.create();
    }
}