package com.example.springauth.models.json.pindo;

import com.example.springauth.models.json.IResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PindoSingleMessageResponseModel implements IResponseModel {

    @JsonProperty("item_count")
    private int itemCount;
    @JsonProperty("item_price")
    private double itemPrice;
    @JsonProperty("report_id")
    private String reportId;
    @JsonProperty("self_url")
    private String selfUrl;
    @JsonProperty("sms_id")
    private String smsId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("to")
    private String toPhoneNumber;
    @JsonProperty("total_cost")
    private double totalCost;

    public PindoSingleMessageResponseModel() {
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getSelfUrl() {
        return selfUrl;
    }

    public void setSelfUrl(String selfUrl) {
        this.selfUrl = selfUrl;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
