package com.example.springauth.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SMSResponseJSONModel {

    @JsonProperty("service_provider")
    private String serviceProvider;
    @JsonProperty("status")
    private String status;
    @JsonProperty("response_code")
    private String responseCode;
    @JsonProperty("message_count")
    private int messageCount;
    @JsonProperty("success_count")
    private int successCount;

    public SMSResponseJSONModel() {
    }

    public SMSResponseJSONModel(int messageCount, String responseCode, String serviceProvider, String status, int successCount) {
        this.messageCount = messageCount;
        this.responseCode = responseCode;
        this.serviceProvider = serviceProvider;
        this.status = status;
        this.successCount = successCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
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

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
}
