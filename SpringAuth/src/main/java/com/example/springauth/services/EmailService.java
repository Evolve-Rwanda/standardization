package com.example.springauth.services;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface EmailService {
    void sendSimpleMail(String from, String to, String subject, String text);
    void sendSimpleMailWithCC(String from, String to, String[] ccList, String subject, String text);
    void sendSimpleMailWithBCC(String from, String to, String[] bccList, String subject, String text);
    void sendMailWithAttachments(String from, String to, String subject, String text, List<String> attachmentPathList);
    void sendMailWithCCAndAttachments(String from, String to, String[] ccList, String subject, String text, List<String> attachmentPathList);
    void sendMailWithBCCAndAttachments(String from, String to, String[] bccList, String subject, String text, List<String> attachmentPathList);
}

