package com.example.springauth.specialtables;


import com.example.springauth.columns.ColumnValueOption;
import com.example.springauth.dialects.postgres.PostgresDialect;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.schemas.Schema;

import java.util.List;



public class TableOfColumnValueOptions extends SpecialTable{

    private final String name = SpecialTableNameGiver.getTableOfColumnValueOptionsName();
    private final Schema schema;

    public TableOfColumnValueOptions(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect, schema);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
        this.setName(name);
    }

    public void documentColumnValueOptions(List<ColumnValueOption> columnList){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name, schema);
            postgresDialect.documentColumnValueOptions(columnList);
        }
    }
}
