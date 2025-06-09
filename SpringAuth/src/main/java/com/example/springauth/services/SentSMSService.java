package com.example.springauth.services;

import com.example.springauth.models.jpa.SentSMS;
import com.example.springauth.repositories.SentSMSRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SentSMSService {

    @Autowired
    private SentSMSRepository sentSMSRepository;

    @Transactional
    public SentSMS saveSentSMS(SentSMS sentSMS) {
        return sentSMSRepository.save(sentSMS);
    }

    public List<SentSMS> saveManySentSMS(List<SentSMS> sentSMSList) {
        return sentSMSRepository.saveAll(sentSMSList);
    }

    @Transactional
    public List<SentSMS> getAllSentSMS() {
        return sentSMSRepository.findAll();
    }
}
