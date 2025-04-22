package com.example.springauth.tables;

import com.example.springauth.columns.Column;
import java.util.List;

public class ContactTable extends Table {

    public ContactTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains contact information about the users " +
                "and different entities in the system or application";
    }
}
