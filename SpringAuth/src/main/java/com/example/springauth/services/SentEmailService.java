package com.example.springauth.services;

import com.example.springauth.models.jpa.Email;
import com.example.springauth.repositories.SentEmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentEmailService {

    @Autowired
    private SentEmailRepository sentEmailRepository;

    @Transactional
    public Email save(Email email) {
        return sentEmailRepository.save(email);
    }

    @Transactional
    public List<Email> saveAll(List<Email> emailList) {
        return sentEmailRepository.saveAll(emailList);
    }
}
