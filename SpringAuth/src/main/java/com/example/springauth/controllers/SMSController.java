package com.example.springauth.controllers;


import com.example.springauth.services.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SMSController {

    /*
    @Autowired
    private SMSService smsService;

    @PostMapping("/send")
    public String sendSMS(@RequestParam String to, @RequestParam String message) {
        smsService.sendSMS(to, message);
        return "SMS sent!";
    }
     */
}
