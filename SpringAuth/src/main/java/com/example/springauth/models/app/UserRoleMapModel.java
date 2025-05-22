package com.example.springauth.models.app;

public class UserRoleMapModel {
    private String userId;
    private String roleId;
    private String createdAt;
    private String lastUpdatedAt;
    private String deletedAt;

    public UserRoleMapModel() {}

    public UserRoleMapModel(String userId, String roleId, String createdAt) {
        this.userId = userId;
        this.roleId = roleId;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserRoleMapModel{" +
                "userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
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
