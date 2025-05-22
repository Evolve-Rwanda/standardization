package com.example.springauth.models.json;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PwdChangeJSONModel {


    @JsonProperty("username")
    private String username;
    @JsonProperty("current_password")
    private String currentPassword;
    @JsonProperty("new_password")
    private String newPassword;
    @JsonProperty("confirm_new_password")
    private String confirmNewPassword;


    public PwdChangeJSONModel() {
    }

    public PwdChangeJSONModel(String username, String currentPassword, String newPassword, String confirmNewPassword) {
        this.username = username;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public String toString() {
        return "PwdChangeJSONModel{" +
                "username='" + username + '\'' +
                ", currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
