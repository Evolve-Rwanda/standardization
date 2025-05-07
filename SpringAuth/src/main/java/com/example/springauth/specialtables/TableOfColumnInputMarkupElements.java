package com.example.springauth.specialtables;

import com.example.springauth.columns.Column;
import com.example.springauth.columns.ColumnMarkupElement;
import com.example.springauth.dialects.postgres.PostgresDialect;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.schemas.Schema;

import java.util.List;
import java.util.Map;



public class TableOfColumnInputMarkupElements extends SpecialTable{


    private final String name = SpecialTableNameGiver.getTableOfColumnInputMarkupElementsName();
    private final Schema schema;

    public TableOfColumnInputMarkupElements(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect, schema);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
        this.setName(name);
    }

    public void documentColumnMarkupElements(List<ColumnMarkupElement> columnMarkupElementList){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name, schema);
            postgresDialect.documentColumnMarkupElements(columnMarkupElementList);
        }
    }

}
