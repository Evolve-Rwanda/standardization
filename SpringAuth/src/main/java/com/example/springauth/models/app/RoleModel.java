package com.example.springauth.models.app;

import java.util.Objects;

public class RoleModel {


    private String id;
    private String code;
    private String name;
    private String description;
    private String createdBy;
    private String status;
    private String createdAt;


    public RoleModel() {
    }

    public RoleModel(String id, String code, String name, String description, String createdBy, String status, String createdAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.status = status;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PrivilegeModel{" +
                "code='" + code + "'" +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoleModel roleModel)) return false;
        return Objects.equals(code, roleModel.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
