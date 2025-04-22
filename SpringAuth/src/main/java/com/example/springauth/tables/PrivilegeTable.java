package com.example.springauth.tables;

import com.example.springauth.columns.Column;
import java.util.List;

public class PrivilegeTable extends Table {
    public PrivilegeTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains information about all the " +
                "different privileges a given role in the system or application has. " +
                "For example, only a super admin has the privilege to add other admins";
    }
}
