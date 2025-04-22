package com.example.springauth.tables;

import com.example.springauth.columns.Column;
import java.util.List;

public class AuthenticationHistoryTable extends Table {
    public AuthenticationHistoryTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains authentication history information for all the " +
                "different users in the system or application.";
    }
}
