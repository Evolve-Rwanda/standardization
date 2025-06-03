package com.example.springauth.models.app;

import java.util.Objects;



public class PrivilegeModel {


    private String id;
    private String code;
    private String name;
    private String description;
    private String approvalMethod;
    private String createdBy;
    private String status;
    private String createdAt;
    private String lastUpdatedAt;
    private String deletedAt;

    public PrivilegeModel() {}

    public PrivilegeModel(
            String id,
            String code,
            String name,
            String description,
            String approvalMethod,
            String createdBy,
            String status,
            String createdAt,
            String lastUpdatedAt,
            String deletedAt
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.approvalMethod = approvalMethod;
        this.createdBy = createdBy;
        this.status = status;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.deletedAt = deletedAt;
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
        if (!(o instanceof PrivilegeModel that)) return false;
        return Objects.equals(code, that.code);
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

    public String getApprovalMethod() {
        return approvalMethod;
    }

    public void setApprovalMethod(String approvalMethod) {
        this.approvalMethod = approvalMethod;
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

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
