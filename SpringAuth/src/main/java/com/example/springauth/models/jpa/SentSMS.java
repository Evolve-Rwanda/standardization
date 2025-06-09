package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Entity
public class SentSMS {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    private String type;

    @Column(length = 255, nullable = false)
    private String serviceProvider;

    @Column(length = 20)
    private String status;

    private int totalMessages;
    private int totalSuccessfulMessages;

    @Column(length = 20)
    private String responseCode;

    // may not necessarily be a phone number but a recode of other entities
    @Column(length = 255, nullable = false)
    private String fromPhoneNumber;

    @Column(length = 30, nullable = false)
    private String toPhoneNumber;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateAndTimeSent;



    public SentSMS() {
    }

    public SentSMS(String id, String type, String serviceProvider, String status, int totalMessages, int totalSuccessfulMessages, String responseCode, String fromPhoneNumber, String toPhoneNumber, String message, LocalDateTime dateAndTimeSent, String metadata) {
        this.fromPhoneNumber = fromPhoneNumber;
        this.id = id;
        this.message = message;
        this.responseCode = responseCode;
        this.serviceProvider = serviceProvider;
        this.status = status;
        this.toPhoneNumber = toPhoneNumber;
        this.totalMessages = totalMessages;
        this.totalSuccessfulMessages = totalSuccessfulMessages;
        this.type = type;
        this.dateAndTimeSent = dateAndTimeSent;
        this.metadata = metadata;
    }

    public String getFromPhoneNumber() {
        return fromPhoneNumber;
    }

    public void setFromPhoneNumber(String fromPhoneNumber) {
        this.fromPhoneNumber = fromPhoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToPhoneNumber() {
        return toPhoneNumber;
    }

    public void setToPhoneNumber(String toPhoneNumber) {
        this.toPhoneNumber = toPhoneNumber;
    }

    public int getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }

    public int getTotalSuccessfulMessages() {
        return totalSuccessfulMessages;
    }

    public void setTotalSuccessfulMessages(int totalSuccessfulMessages) {
        this.totalSuccessfulMessages = totalSuccessfulMessages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateAndTimeSent() {
        return dateAndTimeSent;
    }

    public void setDateAndTimeSent(LocalDateTime dateAndTimeSent) {
        this.dateAndTimeSent = dateAndTimeSent;
    }
}
