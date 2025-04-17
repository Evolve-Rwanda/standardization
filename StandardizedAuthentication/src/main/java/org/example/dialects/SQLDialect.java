package org.example.dialects;

import org.example.tables.Table;

public class SQLDialect {

    protected Table table;

    public SQLDialect(){
    }

    public SQLDialect(Table table){
        this.table = table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

}