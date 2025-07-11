package com.example.springauth.specialtables;

import com.example.springauth.schemas.Schema;
import com.example.springauth.columns.Column;
import com.example.springauth.dialects.postgres.PostgresDialect;
import com.example.springauth.dialects.postgres.QueryExecutor;

import java.util.List;



public class TableOfColumns extends SpecialTable {


    private final String name = SpecialTableNameGiver.getTableOfColumnsName();
    private final Schema schema;

    public TableOfColumns(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect, schema);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
        this.setName(name);
    }

    public void documentColumns(List<Column> columnList){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name, schema);
            postgresDialect.documentColumns(columnList);
        }
    }

}
