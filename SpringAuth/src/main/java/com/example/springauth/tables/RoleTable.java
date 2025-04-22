package com.example.springauth.tables;

import com.example.springauth.columns.Column;

import java.util.List;

public class RoleTable extends Table {

    public RoleTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains information about all the " +
                "different roles users in the system or application can assume at a particular moment";
    }
}
