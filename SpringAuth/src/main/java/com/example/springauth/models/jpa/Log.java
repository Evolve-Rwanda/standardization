package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Log {


    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 30)
    private String timestamp;

    @Column(length = 15)
    private String type;

    @Column(length = 100)
    private String user_id;

    @Column(length = 200)
    private String action;

    @Column(columnDefinition = "TEXT")
    private String contents;

    public Log(){
    }

    public Log(String id, String timestamp, String type, String user_id, String action, String contents) {
        this.action = action;
        this.contents = contents;
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.user_id = user_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }
}
