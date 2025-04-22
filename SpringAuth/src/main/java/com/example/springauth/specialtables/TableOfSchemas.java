package com.example.springauth.specialtables;

import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.schemas.Schema;
import com.example.springauth.dialects.postgres.PostgresDialect;
import java.util.List;



public class TableOfSchemas extends SpecialTable {


    private final String name = SpecialTableNameGiver.getTableOfSchemasName();
    private final Schema schema;


    public TableOfSchemas(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect, schema);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
        this.setName(name);
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
