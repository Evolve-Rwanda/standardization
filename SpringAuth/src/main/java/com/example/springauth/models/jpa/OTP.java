package com.example.springauth.models.jpa;


import com.example.springauth.utilities.security.encryption.StringEncryptorDecrypter;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class OTP {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 20)
    private String destinationType; // Email, Phone number

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime timeSent;

    @Column(length = 20)
    private String status; // used, unused

    @Column(length = 100)
    //@Convert(converter = StringEncryptorDecrypter.class)
    private String code;

    private int timeout;

    public OTP() {
    }

    public OTP(String id, String destinationType, String timeSent, String status, String code, int timeout) {
        this.code = code;
        this.destinationType = destinationType;
        this.id = id;
        this.status = status;
        this.timeout = timeout;
        this.timeSent = LocalDateTime.parse(timeSent);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }
}
