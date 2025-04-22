package com.example.springauth.specialtables;

import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.schemas.Schema;
import com.example.springauth.dialects.postgres.PostgresDialect;
import com.example.springauth.relationships.Relationship;
import java.util.List;



public class TableOfRelationships extends SpecialTable {


    private final String name = SpecialTableNameGiver.getTableOfRelationshipsName();
    private final Schema schema;

    public TableOfRelationships(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect, schema);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
        this.setName(name);
    }

    public void documentRelationships(List<Relationship> relationshipList){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name, schema);
            postgresDialect.documentRelationships(relationshipList);
        }
    }

    public String getName() {
        return name;
    }
}
