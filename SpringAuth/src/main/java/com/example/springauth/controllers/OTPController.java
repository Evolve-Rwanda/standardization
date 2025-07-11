package com.example.springauth.controllers;

import com.example.springauth.models.jpa.OTP;
import com.example.springauth.services.OTPService;
import com.example.springauth.utilities.Configuration;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.OTPGenerator;
import com.example.springauth.utilities.OTPIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springauth.logging.loggers.Logger;


@RestController
public class OTPController {


    @Autowired
    private OTPService otpService;
    Logger logger = Logger.getLogger();


    @GetMapping("/generate_otp")
    public String generateOTP(@RequestParam("destination_type") String destinationType){
        String id = OTPIdGenerator.generateOTPId();
        String timeSent = DateTime.getTimeStampWithoutTimeZone();
        String status = Configuration.VALID_OTP;
        String code = OTPGenerator.generateOTP() + "";
        int timeout = Configuration.OTP_TIMEOUT_IN_SECONDS;
        OTP otp = otpService.saveOTP(new OTP(id, destinationType, timeSent, status, code, timeout));
        // send back an id to the otp, not the actual otp
        // The user is expected to enter the received otp
        // logger.debug("The generated otp is: " + code);
        return String.format("{\"id\":\"%s\", \"code\":\"%s\"}", otp.getId(), "null");
    }

    @GetMapping("/generate_long_otp")
    public String generateLongOTP(@RequestParam("destination_type") String destinationType){
        String id = OTPIdGenerator.generateOTPId();
        String timeSent = DateTime.getTimeStampWithoutTimeZone();
        String status = Configuration.VALID_OTP;
        String code = OTPGenerator.generateOTP() + "";
        int timeout = Configuration.OTP_TIMEOUT_IN_SECONDS;
        OTP otp = otpService.saveOTP(new OTP(id, destinationType, timeSent, status, code, timeout));
        // logger.debug("The generated otp is: " + code);
        return String.format("{\"id\":\"%s\", \"code\":\"%s\"}", otp.getId(), "null");
    }

    @GetMapping("verify_otp")
    public String verifyOTP(@RequestParam("id") String otpId, @RequestParam("code") String code) {
        String currentDateTimeString = DateTime.getTimeStampWithoutTimeZone();
        OTP otp = otpService.getOTPById(otpId);
        if (otp == null)
            return "false";
        String sendingDateTimeString = otp.getTimeSent().toString();
        int savedCode = Integer.parseInt(otp.getCode());
        int confirmation = Integer.parseInt(code.trim());
        int timeOutDuration = otp.getTimeout();
        long elapsedSeconds = DateTime.getDurationInSeconds(sendingDateTimeString, currentDateTimeString);
        boolean isValid = ((long)timeOutDuration >= elapsedSeconds) && (savedCode == confirmation);
        // logger.debug("The saved otp is: " + savedCode);
        // logger.verbose("The elapsed duration is: " + elapsedSeconds + " and the timeout duration is: " + timeOutDuration);
        // logger.verbose("The saved code is: " + savedCode + " and the confirming code is: " + confirmation);
        // add a database targeted log
        return isValid ? "true" : "false";
    }

}
