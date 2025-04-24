package com.example.springauth.models;

public class RelationshipModel {

    private String leftTableName;
    private String rightTableName;
    private String type;
    private String description;


    public RelationshipModel(){

    }

    @Override
    public String toString() {
        return "RelationshipModel{" +
                ", leftTableName='" + leftTableName + '\'' +
                ", rightTableName='" + rightTableName + '\'' +
                ", type='" + type + '\'' +
                " - description='" + description + '\'' +
                '}';
    }

    public String getLeftTableName() {
        return leftTableName;
    }

    public void setLeftTableName(String leftTableName) {
        this.leftTableName = leftTableName;
    }

    public String getRightTableName() {
        return rightTableName;
    }

    public void setRightTableName(String rightTableName) {
        this.rightTableName = rightTableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
