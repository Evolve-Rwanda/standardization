package com.example.springauth.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PindoRecipientModel {


    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("name")
    private String name;


    public PindoRecipientModel() {
    }

    public PindoRecipientModel(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
