package com.example.springauth.controllers;

import com.example.springauth.components.EmailComponent;
import com.example.springauth.models.jpa.Email;
import com.example.springauth.services.SentEmailService;
import com.example.springauth.utilities.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;



@RestController
public class EmailController {


    @Autowired
    private SentEmailService sentEmailService;

    @Autowired
    private EmailComponent emailService;


    @GetMapping("/send_test_mail")
    public String sendTestSimpleEmail(@RequestParam("email") String toEmail) {
        String fromEmail = Configuration.EVOLVE_FROM_EMAIL;
        String subject = "Testing simple mail sending";
        String message = "Hey Maurice, this is a test mail launched from java spring boot to test simple mails";
        emailService.sendSimpleMail(fromEmail, toEmail, subject, message);
        return "true";
    }

    @GetMapping("/send_test_mime_email")
    public String sendTestEmailWithAttachments(@RequestParam("email") String toEmail){
        String fromEmail = Configuration.EVOLVE_FROM_EMAIL;
        String subject = "Email attachment test";
        String message = "Roger! Email attachments have been successfully sent";
        String attachment1Path = "/home/maurice/IdeaProjects/Standardization/standardization/SpringAuth/src/main/resources/static/images/add.png";
        String attachment2Path = "/home/maurice/IdeaProjects/Standardization/standardization/SpringAuth/src/main/resources/static/images/documentation.png";
        String attachment3Path = "/home/maurice/IdeaProjects/Standardization/standardization/SpringAuth/src/main/resources/static/images/view.png";
        List<String> attachmentPathList = new ArrayList<>();
        attachmentPathList.add(attachment1Path);
        attachmentPathList.add(attachment2Path);
        attachmentPathList.add(attachment3Path);
        emailService.sendMailWithAttachments(fromEmail, toEmail, subject, message, attachmentPathList);
        return "true";
    }

    @GetMapping("/send_simple_mail")
    public String sendSimpleEmail(
            @RequestParam("from") String fromEmail,
            @RequestParam("to") String toEmail,
            @RequestParam("subject") String subject,
            @RequestParam("content") String message
    ) {
        // save email record using a sent mail service
        sentEmailService.save(new Email());
        emailService.sendSimpleMail(fromEmail, toEmail, subject, message);
        return "true";
    }

    @GetMapping("/send_mime_email")
    public String sendEmailWithAttachments(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("content") String message,
            @RequestParam("attachment") List<String> attachmentPathList
    ){
        emailService.sendMailWithAttachments(from, to, subject, message, attachmentPathList);
        // create a record of the email in the database for obvious reasons
        sentEmailService.save(new Email());
        // Also create a record of all email attachments
        return "true";
    }
}
