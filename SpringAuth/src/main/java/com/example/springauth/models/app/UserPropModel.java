package com.example.springauth.models.app;

import com.example.springauth.models.utility.ColumnMarkupElementModel;
import com.example.springauth.models.utility.ColumnValueOptionModel;

import java.util.List;


public class UserPropModel {


    private String propertyName;
    private String propertyValue;
    private ColumnMarkupElementModel columnMarkupElementModel;
    private List<ColumnValueOptionModel> columnValueOptionModels;

    public UserPropModel() {
    }

    public UserPropModel(String propertyName, String propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    @Override
    public String toString() {
        return "UserPropModel{" +
                  "propertyName='" + propertyName + "'" + ", propertyValue='" + propertyValue + "'" +
                "}";
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public ColumnMarkupElementModel getColumnMarkupElementModel() {
        return columnMarkupElementModel;
    }

    public void setColumnMarkupElementModel(ColumnMarkupElementModel columnMarkupElementModel) {
        this.columnMarkupElementModel = columnMarkupElementModel;
    }

    public List<ColumnValueOptionModel> getColumnValueOptionModels() {
        return columnValueOptionModels;
    }

    public void setColumnValueOptionModels(List<ColumnValueOptionModel> columnValueOptionModels) {
        this.columnValueOptionModels = columnValueOptionModels;
    }

}
