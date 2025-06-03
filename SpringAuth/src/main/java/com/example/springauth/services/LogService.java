package com.example.springauth.services;

import com.example.springauth.models.jpa.Log;
import com.example.springauth.repositories.LogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LogService {


    @Autowired
    private LogRepository logRepository;

    @Transactional
    public Log createLog(Log log) {
        return logRepository.save(log);
    }

    @Transactional
    public Log findLogById(String id) {
        Optional<Log> log = logRepository.findById(id);
        return log.orElse(null);
    }

    @Transactional
    public List<Log> findAllLogs() {
        return logRepository.findAll();
    }

    @Transactional
    public List<Log> findLogsByPredicatedTime(String startDate, String endDate){
        return null;
    }

}
