package org.example;

import java.util.List;
import java.util.ArrayList;

public class RoleTable extends Table{

    public RoleTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains information about all the " +
                "different roles users in the system or application can assume at a particular moment";
    }
}
