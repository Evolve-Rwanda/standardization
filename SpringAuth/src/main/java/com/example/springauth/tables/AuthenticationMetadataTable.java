package com.example.springauth.tables;

import com.example.springauth.columns.Column;
import java.util.List;

public class AuthenticationMetadataTable extends Table {

    public AuthenticationMetadataTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains authentication history metadata information for all the " +
                "different authentication sessions recorded in the system or application.";
    }
}
