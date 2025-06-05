package com.example.springauth.controllers;

import com.example.springauth.components.EmailComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class EmailController {


    @Autowired
    EmailComponent emailService;


    @GetMapping("/send_test_mail")
    public String sendTestEmail(@RequestParam("email") String testEmail) {
        // keep a record of the email in the database for obvious reasons
        emailService.sendSimpleMail(
                testEmail,
                "Testing the mail module",
                "Hey Maurice, this is a test mail launched from java spring boot."
        );
        return "Successfully sent email";
    }

    @GetMapping("send_mime_email")
    public String sendEmailWithAttachments(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("content") String body,
            @RequestParam("attachment") List<String> attachmentPathList
    ){
        // create a record of the email in the database for obvious reasons
        emailService.sendMailWithAttachments(from, to, subject, body, attachmentPathList);
        return "true";
    }
}
