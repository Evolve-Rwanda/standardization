package com.example.springauth.logging.message;

import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.UUIDGenerator;



public class Message {


    private String id;
    private String timestamp;
    private String type;
    private String user;
    private String action;
    private String contents;


    public Message(String user, String action, String contents){
        this.id = UUIDGenerator.generateUUID();
        this.timestamp = DateTime.getTimeStamp();
        this.user = user;
        this.action = action;
        this.contents = contents;
    }


    public Message(String id, String timestamp, String type, String user, String action, String contents) {
        this.action = action;
        this.contents = contents;
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format(
            "%20s %20s %10s %40s %15s %s",
            id, timestamp, type, user, action, contents
        );
    }

    public String toJSON() {
        return String.format(
            "{\"id\":\"%s\", \"timestamp\":\"%s\", \"type\":\"%s\", \"user\":\"%s\", \"action\":\"%s\", \"contents\":\"%s\"}",
            id, timestamp, type, user, action, contents
        );
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static boolean isJSONEncoded(String message) {
        return message.contains("{") && message.contains(":")
               &&
               message.contains("\"") && message.contains("}");
    }
}
