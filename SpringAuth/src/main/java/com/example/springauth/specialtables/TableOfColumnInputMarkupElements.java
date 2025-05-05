package com.example.springauth.specialtables;

import com.example.springauth.columns.Column;
import com.example.springauth.dialects.postgres.PostgresDialect;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.markup.inputelements.HTMLFormInputElement;
import com.example.springauth.schemas.Schema;

import java.util.Map;



public class TableOfColumnInputMarkupElements extends SpecialTable{


    private final String name = SpecialTableNameGiver.getTableOfColumnValueOptionsName();
    private final Schema schema;

    public TableOfColumnInputMarkupElements(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect, schema);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
        this.setName(name);
    }

    public void documentColumnInputMarkupElements(Map<Column, HTMLFormInputElement> columnInputElementMap){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name, schema);
            //postgresDialect.documentColumns(columnList);
        }
    }

}
