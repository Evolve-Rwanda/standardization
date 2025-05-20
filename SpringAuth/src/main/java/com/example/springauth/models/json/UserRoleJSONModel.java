package com.example.springauth.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserRoleJSONModel {


    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("role_id")
    private String roleId;
    @JsonProperty("status")
    private String status;

    public UserRoleJSONModel() {
        // constructor required by the json reader to work
    }

    public UserRoleJSONModel(String userId, String roleId, String status) {
        this.userId = userId;
        this.roleId = roleId;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
