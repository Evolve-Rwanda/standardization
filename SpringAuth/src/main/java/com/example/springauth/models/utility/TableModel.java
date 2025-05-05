package com.example.springauth.models.utility;

public class TableModel {

    private String schema;
    private String name;
    private String description;

    public TableModel(){
    }

    @Override
    public String toString() {
        return "TableModel{" +
                "schema='" + schema + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
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
}
