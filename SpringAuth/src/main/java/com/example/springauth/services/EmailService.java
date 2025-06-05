package com.example.springauth.services;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface EmailService {
    void sendSimpleMail(String to, String subject, String text);
    void sendMailWithAttachments(String from, String to, String subject, String text, List<String> attachmentPathList);
}

