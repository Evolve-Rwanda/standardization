package org.example;

import java.util.List;


public class TableOfTables  extends SpecialTable{


    private final String name = "table_of_tables";
    private final Schema schema;

    public TableOfTables(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
    }

    public void documentTables(List<Table> tableList){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name, schema);
            postgresDialect.documentTables(tableList);
        }
    }


}
