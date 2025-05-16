package com.example.springauth.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RolePrivilegeJSONModel {

    @JsonProperty("role_id")
    private String roleId;
    @JsonProperty("privilege_id")
    private String privilegeId;
    @JsonProperty("status")
    private String status;

    public RolePrivilegeJSONModel() {
        // this constructor is very important if parsing the json should work.
    }

    public RolePrivilegeJSONModel(String roleId, String privilegeId, String status) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
        this.status = status;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
