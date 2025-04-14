package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;




public class Table {


    protected Schema schema;
    protected String name;
    protected String description;
    protected List<Column> baseColumnList;
    protected List<Column> columnList;
    protected QueryExecutor queryExecutor;
    protected String sqlDialect;

    public Table(String name, List<Column> columnList){
        this.name = name;
        this.columnList = columnList;
        this.baseColumnList = new ArrayList<>();
        this.setDescription(this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Table table)) return false;
        return Objects.equals(name, table.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return name;
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

    public Schema getSchema() {
        return schema;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription() {
        return description;
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

    public String getTableDDLQuery(){
        String tableDDLQuery = "";
        if(sqlDialect.equalsIgnoreCase("postgres")) {
            PostgresDialect postgresDialect = new PostgresDialect(this);
            tableDDLQuery = postgresDialect.getDialectTableDDLQuery();
        }
        return tableDDLQuery;
    }

    public static void createTables(List<Table> tableList){
        for (Table table: tableList)
            table.createTable();
    }

    public void createTable(){
        String tableDDL = this.getTableDDLQuery();
        if(tableDDL != null) {
            queryExecutor.executeQuery(tableDDL);
            queryExecutor.closeResources();
        }
    }

}
