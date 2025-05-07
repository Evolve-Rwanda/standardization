package com.example.springauth.models.utility;


public class ColumnValueOptionModel {

    private String columnId;
    private String optionalValue;

    public ColumnValueOptionModel() {
    }

    public ColumnValueOptionModel(String columnId, String optionalValue) {
        this.columnId = columnId;
        this.optionalValue = optionalValue;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getOptionalValue() {
        return optionalValue;
    }

    public void setOptionalValue(String optionValue) {
        this.optionalValue = optionValue;
    }

}
