package org.example;

import java.util.List;

public class DerivedTable extends Table{
    public DerivedTable(String name, List<Column> columnList){
        super(name, columnList);
    }
}
