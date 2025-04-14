package org.example;

import java.util.List;



public class TableOfRelationships extends SpecialTable{


    private final String name = "table_of_relationships";
    private final Schema schema;

    public TableOfRelationships(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
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
