package com.example.springauth.models.json.pindo;

import com.example.springauth.models.json.IResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PindoBulkMessageResponseModel implements IResponseModel {


    @JsonProperty("count")
    private int count;
    @JsonProperty("item_price")
    private double itemPrice;
    @JsonProperty("report_id")
    private String reportId;
    @JsonProperty("network")
    private String network;
    @JsonProperty("total")
    private int total;
    @JsonProperty("status")
    private String status;
    @JsonProperty("total_cost")
    private double totalCost;

    public PindoBulkMessageResponseModel() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
