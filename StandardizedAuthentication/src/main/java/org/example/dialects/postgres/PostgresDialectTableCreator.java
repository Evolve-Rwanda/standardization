package org.example.dialects.postgres;

import org.example.columns.Column;
import org.example.tables.Table;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;



public class PostgresDialectTableCreator {


    private final Table table;
    private final QueryExecutor queryExecutor;

    public PostgresDialectTableCreator(Table table, QueryExecutor queryExecutor) {
        this.table = table;
        this.queryExecutor = queryExecutor;
    }

    public void createTable(){
        this.createTable(table);
    }

    public void createTable(Table table){
        String tableDDL = this.getTableDDLQuery(table);
        if(tableDDL != null) {
            queryExecutor.executeQuery(tableDDL);
            queryExecutor.closeResources();
        }
    }

    public String getTableDDLQuery(Table table){
        String tableSchema = table.getSchema() != null ? table.getSchema().getName() : "";
        String ddlQuery = "";
        if(!tableSchema.isEmpty())
            ddlQuery = String.format("SET search_path TO %s, public;", tableSchema);
        ddlQuery = ddlQuery + "CREATE TABLE IF NOT EXISTS \"" + table.getName() + "\"(";
        ddlQuery = ddlQuery + constructDDLColumnDeclaration(table);
        ddlQuery = ddlQuery + this.constructPrimaryKeyConstraint(table);
        if (!table.getForeignKeyList().isEmpty())
            ddlQuery = ddlQuery + ", " + this.constructForeignKeyConstraint(table);
        ddlQuery = ddlQuery + ");";
        return ddlQuery;
    }

    private String constructDDLColumnDeclaration(Table table){
        StringBuilder queryBuilder = new StringBuilder();
        for (Column c : table.getUniversalColumnList()) {
            String columnName = c.getName();
            String type = c.getDataType();
            long scale = c.getScale();
            long precision = c.getPrecision();
            String isNull = c.getIsNullable();
            String defaultValue = c.getDefaultValue();
            boolean hasPrecisionAndScale = PostgresType.hasPrecisionAndScale(type);
            boolean hasNoPrecision = PostgresType.hasNoPrecision(type);
            boolean isNullable = isNull != null && isNull.equalsIgnoreCase("true");
            boolean hasDefaultValue = defaultValue != null && !defaultValue.trim().isEmpty();
            String columnDeclaration = "";

            if(hasPrecisionAndScale) {
                columnDeclaration = columnName + " " + type + "(" + precision + ", " + scale + ")";
            } else if (hasNoPrecision) {
                columnDeclaration = columnName + " " + type;
            } else{
                columnDeclaration = columnName + " " + type;
                columnDeclaration = (precision > 0) ? columnDeclaration + "(" + precision + ")" : columnDeclaration;
            }
            columnDeclaration = (isNullable) ? columnDeclaration : columnDeclaration + " NOT NULL";
            if(hasDefaultValue)
                columnDeclaration = columnDeclaration + "DEFAULT " + defaultValue;
            queryBuilder.append(columnDeclaration);
            queryBuilder.append(", ");
        }
        return queryBuilder.toString();
    }

    private String constructPrimaryKeyConstraint(Table table){
        StringBuilder constraintBuilder = new StringBuilder();
        constraintBuilder.append("PRIMARY KEY(");
        List<Column> primaryKeyColumnList = table.getPrimaryKeyList();
        int pkSize = primaryKeyColumnList.size();
        int k = 0;
        for(Column c: primaryKeyColumnList){
            constraintBuilder.append(c.getName());
            if((k+1) < pkSize)
                constraintBuilder.append(", ");
            k++;
        }
        constraintBuilder.append(")");
        return constraintBuilder.toString();
    }

    private String constructForeignKeyConstraint(Table table){
        String tableSchema = table.getSchema() != null ? table.getSchema().getName() + "." : "";
        List<List<Column>> listOfLists = this.groupColumnsByReferenceTableName(table.getForeignKeyList());
        StringBuilder constraintBuilder = new StringBuilder();
        int lolSize = listOfLists.size();
        int t = 0;
        for(List<Column> foreignKeyColumnList: listOfLists) {
            String constraintName = ("fk_" + tableSchema.toLowerCase() + table.getName() + "_" + t).replace(".", "_");
            String referenceTableName = foreignKeyColumnList.getFirst().getReferenceTableName();
            constraintBuilder.append("CONSTRAINT ");
            constraintBuilder.append(constraintName);
            constraintBuilder.append(" FOREIGN KEY(");
            int f = 0, i = 0, fkSize = foreignKeyColumnList.size();
            for (Column c : foreignKeyColumnList) {
                constraintBuilder.append(c.getName());
                if ((f + 1) < fkSize)
                    constraintBuilder.append(", ");
                f++;
            }
            constraintBuilder.append(") REFERENCES ").append(tableSchema + referenceTableName);
            constraintBuilder.append("(");
            for (Column c : foreignKeyColumnList) {
                constraintBuilder.append(c.getReferenceColumnName());
                if ((i + 1) < fkSize)
                    constraintBuilder.append(", ");
                i++;
            }
            constraintBuilder.append(") ON UPDATE CASCADE ON DELETE CASCADE");
            if ((t + 1) < lolSize)
                constraintBuilder.append(", ");
            t++;
        }
        return constraintBuilder.toString();
    }

    private List<List<Column>> groupColumnsByReferenceTableName(List<Column> columnList){
        Map<String, List<Column>> tableNameColumnListMap = new HashMap<>();
        for (Column column: columnList){
            String referenceTableName = column.getReferenceTableName().toUpperCase().replace("\"", "");
            if (!tableNameColumnListMap.containsKey(referenceTableName)){
                List<Column> tableColumnList = new ArrayList<>();
                tableColumnList.add(column);
                tableNameColumnListMap.put(referenceTableName, tableColumnList);
            }else{
                if (!tableNameColumnListMap.get(referenceTableName).contains(column))
                    tableNameColumnListMap.get(referenceTableName).add(column);
            }
        }
        return new ArrayList<>(tableNameColumnListMap.values());
    }
}
