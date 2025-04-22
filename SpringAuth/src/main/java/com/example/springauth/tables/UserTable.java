package com.example.springauth.tables;

import com.example.springauth.columns.Column;
import java.util.List;


public class UserTable extends Table {

    public UserTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains information about all the different users in the system or application";
    }
}
