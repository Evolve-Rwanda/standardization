package org.example;

import java.util.List;



public class TableOfSchemas extends SpecialTable{


    private final String name = "table_of_schemas";
    private final Schema schema;


    public TableOfSchemas(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
    }

    public void createDatabaseSchemas(List<Schema> schemaList){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name);
            postgresDialect.createDatabaseSchemas(schemaList);
        }
    }

    public void documentSchemas(List<Schema> schemaList){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name, schema);
            postgresDialect.documentSchemas(schemaList);
        }
    }

}
