package com.example.springauth.models.app;

import com.example.springauth.models.utility.ColumnMarkupElementModel;
import com.example.springauth.models.utility.ColumnValueOptionModel;

import java.util.ArrayList;
import java.util.List;



public class EntityPropModel {



    private String name;
    private String value;
    private ColumnMarkupElementModel columnMarkupElementModel;
    private List<ColumnValueOptionModel> columnValueOptionModels;


    public EntityPropModel() {
    }

    public EntityPropModel(String name, String value) {
        this.name = name;
        this.value = value;
        this.columnValueOptionModels = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "EntityPropModel{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public void addColumnValueOptionModel(ColumnValueOptionModel columnValueOptionModel) {
        this.columnValueOptionModels.add(columnValueOptionModel);
    }

}
