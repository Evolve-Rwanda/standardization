package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Entity
public class Email {

    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    private String source;

    @Column(length = 50)
    private String destination;

    @Column(columnDefinition = "TEXT")
    private String CC;

    @Column(columnDefinition = "TEXT")
    private String BCC;

    @Column(length = 50)
    private String replyTo;

    @Column(length = 300)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(length = 20)
    private String type; // reply, no-reply

    @Column(length = 10)
    private String priority;

    private LocalDateTime dateTimeSent;

    // except the id, type - all the other fields can be encrypted

    public Email() {
    }

    public Email(
            String id, String source, String destination,
            String CC, String BCC, String replyTo,
            String subject, String body, String type,
            String priority, LocalDateTime dateTimeSent
    ) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.CC = CC;
        this.BCC = BCC;
        this.replyTo = replyTo;
        this.subject = subject;
        this.type = type;
        this.body = body;
        this.priority = priority;
        this.dateTimeSent = dateTimeSent;
    }

    public String getBCC() {
        return BCC;
    }

    public void setBCC(String BCC) {
        this.BCC = BCC;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(LocalDateTime dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }
}
