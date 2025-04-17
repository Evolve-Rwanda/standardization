package org.example.specialtables;

import org.example.schemas.Schema;
import org.example.tables.Table;
import org.example.dialects.postgres.QueryExecutor;
import org.example.dialects.postgres.PostgresDialect;
import java.util.List;


public class TableOfTables  extends SpecialTable {


    private final String name = SpecialTableName.getTableOfTables();
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
