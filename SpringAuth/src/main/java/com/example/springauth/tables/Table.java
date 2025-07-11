package com.example.springauth.tables;

import com.example.springauth.columns.Column;
import com.example.springauth.dialects.postgres.PostgresDialectTableCreator;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.schemas.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;




public class Table {



    protected Schema schema;
    protected String name;
    protected String description;
    protected List<Column> baseColumnList;
    protected List<Column> columnList;
    protected QueryExecutor queryExecutor;
    protected String sqlDialect;

    public Table(){

    }

    public Table(String name, List<Column> columnList){
        this.name = name;
        this.columnList = columnList;
        this.baseColumnList = new ArrayList<>();
        this.setDescription(this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Table table)) return false;
        return Objects.equals(this.getFullyQualifiedName(), table.getFullyQualifiedName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSqlDialect(String sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    public void setBaseColumnList(List<Column> baseColumnList) {
        this.baseColumnList = baseColumnList;
    }

    public void setQueryExecutor(QueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    public void addColumn(Column column){
        if (!columnList.contains(column)) {
            this.columnList.add(column);
            this.reAssignColumnNumbers();
        }
    }

    public String getFullyQualifiedName(){
        String schema = this.schema!= null ? this.schema.getName() : "";
        return !schema.isEmpty() ? (schema + "." + this.name) : this.name;
    }

    public Schema getSchema() {
        return schema;
    }

    public String getName(){
        return this.name.toLowerCase();
    }

    public String getDescription() {
        return description;
    }

    public String getSqlDialect() {
        return sqlDialect;
    }

    public QueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    public List<Column> getPrimaryKeyList(){
        List<Column> primaryKeyList = new ArrayList<>();
        for(Column column: this.getUniversalColumnList()){
            if(column.getIsPK().equalsIgnoreCase("true"))
                primaryKeyList.add(column);
        }
        return primaryKeyList;
    }

    public List<Column> getForeignKeyList(){
        List<Column> foreignKeyList = new ArrayList<>();
        for(Column column: this.getUniversalColumnList()){
            if(column.getIsFK().equalsIgnoreCase("true"))
                foreignKeyList.add(column);
        }
        return foreignKeyList;
    }

    public List<Column> getUniversalColumnList(){
        List<Column> universalColumnList = new ArrayList<>(this.columnList);
        if(baseColumnList != null && !baseColumnList.isEmpty()) {
            for (Column column : baseColumnList) {
                int newNumber = universalColumnList.size() + 1;
                column.setNumber(newNumber);
                universalColumnList.add(column);
            }
        }
        universalColumnList = this.reAssignColumnNumbers();
        return universalColumnList;
    }

    private List<Column> reAssignColumnNumbers(){
        List<Column> newUniversalColumnList = new ArrayList<>();
        int n = 1;
        for(Column column: this.columnList){
            if (!newUniversalColumnList.contains(column)) {
                column.setNumber(n);
                newUniversalColumnList.add(column);
                n++;
            }
        }
        for(Column column: this.baseColumnList){
            if (!newUniversalColumnList.contains(column)) {
                column.setNumber(n);
                newUniversalColumnList.add(column);
                n++;
            }
        }
        return newUniversalColumnList;
    }

    // move this to the dialect class
    public static void createTables(List<Table> tableList){
        for (Table table: tableList) {
            if (table.getSqlDialect().equalsIgnoreCase("postgres")) {
                var postgresTableCreator = new PostgresDialectTableCreator(table, table.getQueryExecutor());
                postgresTableCreator.createTable();
            }
        }
    }

}
