package org.example;

import java.util.List;



public class TableOfColumns extends SpecialTable{

    private final String name = "table_of_columns";
    private final Schema schema;

    public TableOfColumns(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
    }

    public void documentColumns(List<Column> columnList){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name, schema);
            postgresDialect.documentColumns(columnList);
        }
    }

}
