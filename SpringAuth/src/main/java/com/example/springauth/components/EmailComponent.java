package com.example.springauth.components;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.springauth.services.EmailService;

import java.io.File;
import java.util.List;


@Service
public class EmailComponent implements EmailService {


    @Autowired
    private JavaMailSender emailSender;


    @Override
    public void sendSimpleMail(String from, String to, String subject, String text) {
        SimpleMailMessage message = getSimpleMailMessage(from, to, subject, text);
        emailSender.send(message);
    }

    @Override
    public void sendSimpleMailWithCC(String from, String to, String[] ccList, String subject, String text) {
        SimpleMailMessage message = getSimpleMailMessage(from, to, subject, text);
        message.setCc(ccList);
        emailSender.send(message);
    }

    @Override
    public void sendSimpleMailWithBCC(String from, String to, String[] bccList, String subject, String text) {
        SimpleMailMessage message = getSimpleMailMessage(from, to, subject, text);
        message.setBcc(bccList);
        emailSender.send(message);
    }

    @Override
    public void sendMailWithAttachments(String from, String to, String subject, String text, List<String> attachmentPathList) {
        MimeMessage message = emailSender.createMimeMessage();
        sendMailWithBCCAndAttachments(
                false, false, from, to, null, subject, text, attachmentPathList
        );
    }

    @Override
    public void sendMailWithCCAndAttachments(String from, String to, String[] ccList, String subject, String text, List<String> attachmentPathList) {
        sendMailWithBCCAndAttachments(
                true, false, from, to, ccList, subject, text, attachmentPathList
        );
    }

    @Override
    public void sendMailWithBCCAndAttachments(String from, String to, String[] bccList, String subject, String text, List<String> attachmentPathList) {
        sendMailWithBCCAndAttachments(
                true, true, from, to, bccList, subject, text, attachmentPathList
        );
    }

    @NotNull
    private SimpleMailMessage getSimpleMailMessage(String from, String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

    private void sendMailWithBCCAndAttachments(
            boolean hasAMailList,
            boolean isBcc,
            String from,
            String to,
            String[] mailList,
            String subject,
            String text,
            List<String> attachmentPathList
    ) {

        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            if (hasAMailList && isBcc) {
                helper.setBcc(mailList);
            }
            if (hasAMailList && !isBcc) {
                helper.setCc(mailList);
            }
            for (String path: attachmentPathList) {
                FileSystemResource file = new FileSystemResource(new File(path));
                helper.addAttachment(file.getFilename(), file);
            }
            emailSender.send(message);
        }catch (MessagingException e){
            System.out.println(e.getMessage());
            // logging will be done here.
        }
    }

}
