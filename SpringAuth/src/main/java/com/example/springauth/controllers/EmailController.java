package com.example.springauth.controllers;

import com.example.springauth.components.EmailComponent;
import com.example.springauth.models.jpa.Email;
import com.example.springauth.services.SentEmailService;
import com.example.springauth.utilities.Configuration;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.EmailIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
            @RequestParam("reply_to") String replyTo,
            @RequestParam("subject") String subject,
            @RequestParam("content") String message
    ) {
        // save email record using a sent mail service
        sentEmailService.save(
                new Email(
                        EmailIdGenerator.generateEmailId(), fromEmail, toEmail,
                        "", "", replyTo,
                        subject, message, "",
                        "MEDIUM", LocalDateTime.parse(DateTime.getTimeStampWithoutTimeZone())
                )
        );
        emailService.sendSimpleMail(fromEmail, toEmail, subject, message);
        return "true";
    }

    // an end point for sending multiple simple mail cc
    @GetMapping("/send_simple_mail_with_cc")
    public String sendSimpleEmailWithCC(
            @RequestParam("from") String fromEmail,
            @RequestParam("to") String toEmail,
            @RequestParam("reply_to") String replyTo,
            @RequestParam("cc") String ccListString,
            @RequestParam("subject") String subject,
            @RequestParam("content") String message
    ) {

        Email email = new Email(
                EmailIdGenerator.generateEmailId(), fromEmail, toEmail,
                ccListString, "", replyTo,
                subject, message, "",
                "MEDIUM", LocalDateTime.parse(DateTime.getTimeStampWithoutTimeZone())
        );

        // persisting the email in the database for record keeping
        sentEmailService.save(email);
        // actual sending of the email
        emailService.sendSimpleMailWithCC(fromEmail, toEmail, ccListString.split(","), subject, message);
        return "true";
    }


    // an end point for sending multiple simple mail bcc
    @GetMapping("/send_simple_mail_with_bcc")
    public String sendSimpleEmailWithBCC(
            @RequestParam("from") String fromEmail,
            @RequestParam("to") String toEmail,
            @RequestParam("reply_to") String replyTo,
            @RequestParam("bcc") String bccListString,
            @RequestParam("subject") String subject,
            @RequestParam("content") String message
    ) {

        Email email = new Email(
                EmailIdGenerator.generateEmailId(), fromEmail, toEmail,
                "", bccListString, replyTo,
                subject, message, "",
                "MEDIUM", LocalDateTime.parse(DateTime.getTimeStampWithoutTimeZone())
        );

        // persisting the email in the database for record keeping
        sentEmailService.save(email);
        // actual sending of the email
        emailService.sendSimpleMailWithBCC(fromEmail, toEmail, bccListString.split(","), subject, message);
        return "true";
    }


    // An endpoint for sending email with attachments
    @GetMapping("/send_mime_email")
    public String sendEmailWithAttachments(
            @RequestParam("from") String fromEmail,
            @RequestParam("to") String toEmail,
            @RequestParam("reply_to") String replyTo,
            @RequestParam("subject") String subject,
            @RequestParam("content") String message,
            @RequestParam("attachment") List<String> attachmentPathList
    ){
        // send the email to the destination
        emailService.sendMailWithAttachments(fromEmail, toEmail, subject, message, attachmentPathList);
        // create a record of the email in the database for obvious reasons
        sentEmailService.save(
                new Email(
                        EmailIdGenerator.generateEmailId(), fromEmail, toEmail,
                        "", "", replyTo,
                        subject, message, "",
                        "MEDIUM", LocalDateTime.parse(DateTime.getTimeStampWithoutTimeZone())
                )
        );
        return "true";
    }


    // an endpoint for sending multiple mail with attachments using cc
    @GetMapping("/send_mime_email_with_cc")
    public String sendEmailWithCCAndAttachments(
            @RequestParam("from") String fromEmail,
            @RequestParam("to") String toEmail,
            @RequestParam("reply_to") String replyTo,
            @RequestParam("cc") String ccListString,
            @RequestParam("subject") String subject,
            @RequestParam("content") String message,
            @RequestParam("attachment") List<String> attachmentPathList
    ){
        // send the email to the destination
        String[] ccArray = ccListString.split(",");
        emailService.sendMailWithCCAndAttachments(fromEmail, toEmail, ccArray, subject, message, attachmentPathList);
        // create a record of the email in the database for obvious reasons
        sentEmailService.save(
                new Email(
                        EmailIdGenerator.generateEmailId(), fromEmail, toEmail,
                        ccListString, "", replyTo,
                        subject, message, "",
                        "MEDIUM", LocalDateTime.parse(DateTime.getTimeStampWithoutTimeZone())
                )
        );
        return "true";
    }

    // an endpoint for sending multiple mail with attachments using bcc
    @GetMapping("/send_mime_email_with_bcc")
    public String sendEmailWithBCCAndAttachments(
            @RequestParam("from") String fromEmail,
            @RequestParam("to") String toEmail,
            @RequestParam("reply_to") String replyTo,
            @RequestParam("bcc") String bccListString,
            @RequestParam("subject") String subject,
            @RequestParam("content") String message,
            @RequestParam("attachment") List<String> attachmentPathList
    ){
        // send the email to the destination
        String[] bccArray = bccListString.split(",");
        emailService.sendMailWithBCCAndAttachments(fromEmail, toEmail, bccArray, subject, message, attachmentPathList);
        // create a record of the email in the database for obvious reasons
        sentEmailService.save(
                new Email(
                        EmailIdGenerator.generateEmailId(), fromEmail, toEmail,
                        "", bccListString, replyTo,
                        subject, message, "",
                        "MEDIUM", LocalDateTime.parse(DateTime.getTimeStampWithoutTimeZone())
                )
        );
        return "true";
    }
}
