package com.example.springauth.specialtables;

import com.example.springauth.schemas.Schema;
import com.example.springauth.tables.Table;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.dialects.postgres.PostgresDialect;
import java.util.List;


public class TableOfTables  extends SpecialTable {


    private final String name = SpecialTableNameGiver.getTableOfTablesName();
    private final Schema schema;

    public TableOfTables(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        super(queryExecutor, sqlDialect, schema);
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
        this.setName(name);
    }

    public void documentTables(List<Table> tableList){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, name, schema);
            postgresDialect.documentTables(tableList);
        }
    }

    public String getName(){
        return this.name;
    }


}
