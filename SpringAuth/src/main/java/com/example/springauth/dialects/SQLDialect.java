package com.example.springauth.dialects;

import com.example.springauth.tables.Table;

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