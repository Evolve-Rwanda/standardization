package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;




class PostgresDialect extends SQLDialect {


    private String[] numericTypeArray = {
            "smallint", "integer", "bigint",
            "decimal", "numeric", "real",
            "double precision", "smallserial", "serial",
            "bigserial", "money"
    };
    private String[] nonNumericTypeArray = {
            "bit", "bit varying", "boolean",
            "char", "character varying", "character",
            "varchar", "date", "timestamp",
            "text", "time"
    };
    private String[] numericWithScaleTypeArray = {"decimal", "numeric"};

    private String[] precisionlessTypeArray = {"date", "timestamp", "text", "time", "serial", "smallint"};

    private Schema schema;
    private QueryExecutor queryExecutor;
    private String documentingTableName;
    private List<String> databaseSysSchemaList;

    {
        databaseSysSchemaList = new ArrayList<>();
        databaseSysSchemaList.add("information_schema");
        databaseSysSchemaList.add("pg_catalog");
        databaseSysSchemaList.add("pg_toast");
    }


    public PostgresDialect(Table table){
        super(table);
        this.setTable(table);
    }

    // Constructor used in the selection of schemas from the database
    public PostgresDialect(QueryExecutor queryExecutor){
        this.queryExecutor = queryExecutor;
    }

    // Constructor used in the physical creation of schemas in the database, except for the public schema
    // In postgres, the public schema is created as a must, so it need not be created again
    public PostgresDialect(QueryExecutor queryExecutor, String documentingTableName){
        this.queryExecutor = queryExecutor;
        this.documentingTableName = documentingTableName;
    }

    // Constructor used in documenting schemas, tables, relationships, and columns in the same database they exist.
    // Specifically in the table of schemas, table of tables, table of relationships, and table of columns respectively.
    // The name of the table that stores documentation information is given by the second parameter
    // The schema in which the documenting table can be found is given by the third parameter.
    public PostgresDialect(QueryExecutor queryExecutor, String documentingTableName, Schema schema){
        this.queryExecutor = queryExecutor;
        this.documentingTableName = documentingTableName;
        this.schema = schema;
    }

    public String[] getPrecisionlessTypeArray() {
        return precisionlessTypeArray;
    }

    public List<String> getDatabaseSysSchemaList() {
        return databaseSysSchemaList;
    }

    public void createDatabaseSchemas(List<Schema> schemaList){
        if(schemaList == null || schemaList.isEmpty()) {
            throw new NullPointerException("Cannot create a schemas from an empty or null list of schemas");
        }
        for(Schema schema: schemaList) {
            String schemaDDLQuery = null;
            try{
                schemaDDLQuery = this.getDialectCreateSchemaDDLQuery(schema);
                if (schemaDDLQuery != null){
                    queryExecutor.executeQuery(schemaDDLQuery);
                    queryExecutor.closeResources();
                }
            } catch (NullPointerException e) {
                System.out.println("Error generating postgres schema creation DDL for the schema - " + schema);
            }
        }
    }

    public String getDialectCreateSchemaDDLQuery(Schema schema){
        return "CREATE SCHEMA IF NOT EXISTS " + schema.getName() + ";";
    }

    public void documentSchemas(List<Schema> schemaList){
        if(schemaList == null || schemaList.isEmpty()) {
            throw new NullPointerException("Cannot add schemas to the table of schemas from an empty or null list of schemas");
        }
        List<String> insertionQueryList = new ArrayList<>();
        List<Schema> existingSchemaList = new ArrayList<>();
        String setPathQuery = getSchemaPathQuery();
        for(Schema otherSchema: schemaList) {
            Map<String, String> fieldNameValueMap = new HashMap<>();
            fieldNameValueMap.put("schema_name", "'" + otherSchema.getName() + "'");
            SpecialTable.EntryChecker schemaChecker = new SpecialTable.EntryChecker(queryExecutor, documentingTableName, schema, fieldNameValueMap);
            boolean schemaExists = schemaChecker.entryExists();
            if (schemaExists)
                existingSchemaList.add(otherSchema);
        }
        for(Schema schema: schemaList) {
            if(existingSchemaList.contains(schema))
                continue;
            insertionQueryList.add(setPathQuery + this.getTableOfSchemasInsertionQuery(schema));
        }
        if(!insertionQueryList.isEmpty()) {
            queryExecutor.executeQueryList(insertionQueryList);
            queryExecutor.closeResources();
        }
    }

    public void documentTables(List<Table> tableList){
        if(tableList == null || tableList.isEmpty()){
            throw new NullPointerException("Cannot add tables to the table of tables from an empty or null list of tables");
        }
        List<String> insertionQueryList = new ArrayList<>();
        List<Table> existingTableList = new ArrayList<>();
        String setPathQuery = getSchemaPathQuery();
        for(Table table: tableList) {
            String schemaName = table.getSchema() != null ? table.getSchema().getName() : "";
            String tableName = table.getName();
            Map<String, String> fieldnameValueMap = new HashMap<>();
            fieldnameValueMap.put("table_name", "'" + tableName + "'");
            if(!schemaName.isEmpty())
                fieldnameValueMap.put("schema_name", "'" + schemaName + "'");
            SpecialTable.EntryChecker tableChecker = new SpecialTable.EntryChecker(queryExecutor, documentingTableName, schema, fieldnameValueMap);
            boolean tableExists = tableChecker.entryExists();
            if (tableExists)
                existingTableList.add(table);
        }
        for(Table table: tableList) {
            if(existingTableList.contains(table))
                continue;
            insertionQueryList.add(setPathQuery + this.getTableOfTablesInsertionQuery(table));
        }
        if(!insertionQueryList.isEmpty()) {
            queryExecutor.executeQueryList(insertionQueryList);
            queryExecutor.closeResources();
        }
    }

    public void documentRelationships(List<Relationship> relationshipList){
        if(relationshipList == null || relationshipList.isEmpty()){
            throw new NullPointerException("Cannot add relationships to the table of relationships from an empty or null list of relationships");
        }
        List<String> insertionQueryList = new ArrayList<>();
        List<Relationship> existingRelationList = new ArrayList<>();
        String setPathQuery = getSchemaPathQuery();
        for(Relationship relationship: relationshipList) {
            Map<String, String> fieldNameValueMap = new HashMap<>();
            fieldNameValueMap.put("left_table_name", "'" + relationship.getLeftTable().getName() + "'");
            fieldNameValueMap.put("right_table_name", "'" + relationship.getRightTable().getName() + "'");
            SpecialTable.EntryChecker relationshipChecker = new SpecialTable.EntryChecker(queryExecutor, documentingTableName, schema, fieldNameValueMap);
            boolean relationshipExists = relationshipChecker.entryExists();
            if (relationshipExists)
                existingRelationList.add(relationship);
        }
        for(Relationship relationship: relationshipList) {
            if(existingRelationList.contains(relationship))
                continue;
            insertionQueryList.add(setPathQuery + this.getTableOfRelationshipsInsertionQuery(relationship));
        }
        if(!insertionQueryList.isEmpty()) {
            queryExecutor.executeQueryList(insertionQueryList);
            queryExecutor.closeResources();
        }
    }

    public void documentColumns(List<Column> columnList){
        if(columnList == null || columnList.isEmpty())
            return;
        List<String> insertionQueryList = new ArrayList<>();
        List<Column> existingColumnList = new ArrayList<>();
        String setPathQuery = getSchemaPathQuery();
        for(Column column: columnList) {
            String hostTable = column.getTableName();
            String columName = column.getName();
            Map<String, String> fieldNameValueMap = new HashMap<>();
            fieldNameValueMap.put("table_name", "'" + hostTable + "'");
            fieldNameValueMap.put("column_name", "'" + columName + "'");
            SpecialTable.EntryChecker columnChecker = new SpecialTable.EntryChecker(queryExecutor, documentingTableName, schema, fieldNameValueMap);
            boolean columnExists = columnChecker.entryExists();
            if(columnExists)
                existingColumnList.add(column);
        }
        for(Column column: columnList) {
            if(existingColumnList.contains(column))
                continue;
            insertionQueryList.add(setPathQuery + this.getTableOfColumnsInsertionQuery(column));
        }
        if(!insertionQueryList.isEmpty()) {
            queryExecutor.executeQueryList(insertionQueryList);
            queryExecutor.closeResources();
        }
    }

    public String getTableOfSchemasInsertionQuery(Schema someSchema){
        String targetColumns = "schema_name, description, created_at";
        String schemaName = someSchema.getName();
        String description = someSchema.getDescription();
        String createdAt = someSchema.getCreatedAt();
        String columnValues = "'" + schemaName + "', '" + description + "', " + "'" + createdAt + "'";
        return "INSERT INTO \"" + documentingTableName + "\"(" + targetColumns + ") VALUES(" + columnValues + ");";
    }


    public String getTableOfTablesInsertionQuery(Table someTable){
        String targetColumns = "table_name, schema_name, description, created_at";
        String tableName = someTable.getName();
        String schemaName = someTable.getSchema().getName();
        String description = someTable.getDescription();
        String columnValues = "'" + tableName + "', '" + schemaName + "', '" + description + "', " + "'" + DateTime.getTimeStamp() + "'";
        return "INSERT INTO \"" + documentingTableName + "\"(" + targetColumns + ") VALUES(" + columnValues + ");";
    }

    public String getTableOfRelationshipsInsertionQuery(Relationship relationship){
        String targetColumns = "left_table_name, right_table_name, type, created_at";
        String leftTableName = relationship.getLeftTable().getName();
        String rightTableName = relationship.getRightTable().getName();
        String type = relationship.getType();
        String createdAt = DateTime.getTimeStamp();
        String columnValues = "'" + leftTableName + "', '" + rightTableName + "', '" + type + "', '" + createdAt + "'";
        return "INSERT INTO \"" + documentingTableName + "\"(" + targetColumns + ") VALUES(" + columnValues + ");";
    }

    public String getTableOfColumnsInsertionQuery(Column column){
        String targetColumns = (
                "table_name, column_number, column_name, column_data_type, precision, scale, " +
                "default_value, is_nullable, is_pk, is_fk, reference_table_name, reference_column_name, " +
                "on_update_action, on_delete_action, is_a_fact_based_column, is_encrypted, is_indexed, description, created_at"
        );
        String tableName = column.getTableName();
        String columnName = column.getName();
        int columnNumber = column.getNumber();
        String type = column.getDataType();
        long precision = column.getPrecision();
        long scale = column.getScale();
        String defaultValue = column.getDefaultValue();
        String isNullable = column.getIsNullable();
        String isPK = column.getIsPK();
        String isFK = column.getIsFK();
        String referenceTableName = column.getReferenceTableName();
        String referenceColumnName = column.getReferenceColumnName();
        String onUpdateAction = column.getOnUpdateAction();
        String onDeleteAction = column.getOnDeleteAction();
        String isAFactBasedColumn = column.getIsAFactBasedColumn();
        String isEncrypted = column.getIsEncrypted();
        String isIndexed = column.getIsIndexed();
        String description = column.getDescription();
        String createdAt = column.getCreatedAt();

        String columnValues = (
                "'" + tableName + "'," + columnNumber + ", " + "'" + columnName + "'," + "'" + type + "'," + precision + ", " + scale + ", " +
                "'" + defaultValue + "'," + "'" + isNullable + "'," + "'" + isPK + "'," + "'" + isFK + "'," + "'" + referenceTableName + "'," +
                "'" + referenceColumnName + "'," + "'" + onUpdateAction + "'" + ", " + "'" + onDeleteAction + "'," + "'" + isAFactBasedColumn + "'," +
                "'" + isEncrypted + "'," + "'" + isIndexed + "'," + "'" + description + "'," + "'" + createdAt + "'"
        );
        return "INSERT INTO \"" + documentingTableName + "\"(" + targetColumns + ") VALUES(" + columnValues + ");";
    }

    @Override
    public String getDialectTableDDLQuery(){
        String tableSchema = table.getSchema() != null ? table.getSchema().getName() + "." : "";
        String ddlQuery = "";
        if(!tableSchema.isEmpty())
            ddlQuery = String.format("SET search_path TO %s, public;", tableSchema.replace(".", ""));
        ddlQuery = ddlQuery + "CREATE TABLE IF NOT EXISTS \"" + table.getName() + "\"(";
        ddlQuery = ddlQuery + constructDDLColumnDeclaration();
        ddlQuery = ddlQuery + this.constructPrimaryKeyConstraint();
        if (!this.table.getForeignKeyList().isEmpty())
            ddlQuery = ddlQuery + ", " + this.constructForeignKeyConstraint();
        ddlQuery = ddlQuery + ");";
        return ddlQuery;
    }

    private String constructDDLColumnDeclaration(){
        StringBuilder queryBuilder = new StringBuilder();
        for (Column c : table.getUniversalColumnList()) {
            String columnName = c.getName();
            String type = c.getDataType();
            long scale = c.getScale();
            long precision = c.getPrecision();
            String isNull = c.getIsNullable();
            String defaultValue = c.getDefaultValue();
            boolean hasPrecisionAndScale = this.hasPrecisionAndScale(type);
            boolean hasNoPrecision = this.hasNoPrecision(type);
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

    private String constructPrimaryKeyConstraint(){
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

    private String constructForeignKeyConstraint(){
        String tableSchema = table.getSchema() != null ? table.getSchema().getName() + "." : "";
        List<List<Column>> listOfLists = this.groupColumnsByReferenceTableName(this.table.getForeignKeyList());
        StringBuilder constraintBuilder = new StringBuilder();
        int lolSize = listOfLists.size();
        int t = 0;
        for(List<Column> foreignKeyColumnList: listOfLists) {
            String constraintName = "fk_" + tableSchema.replace(".", "_").toLowerCase() + this.table.getName() + "_" + t;
            String referenceTableName = tableSchema + foreignKeyColumnList.getFirst().getReferenceTableName();
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
            constraintBuilder.append(") REFERENCES \"");
            constraintBuilder.append(referenceTableName);
            constraintBuilder.append("\"(");
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


    private String getSchemaPathQuery(){
        return String.format("SET search_path TO %s;", schema.getName());
    }

    public List<String> getDatabaseSchemaNameList(){
        List<String> schemaNameList = new ArrayList<>();
        String schemaSelectionQuery = this.getAllSchemasInTheDatabaseQuery();
        ResultWrapper resultWrapper = queryExecutor.executeQuery(schemaSelectionQuery);
        ResultSet resultSet = resultWrapper.getResultSet();
        try {
            while (resultSet.next()) {
                String schemaName = resultSet.getString("schema_name");
                if (!databaseSysSchemaList.contains(schemaName))
                    schemaNameList.add(schemaName);
            }
        }catch (SQLException e){
            System.out.println("Error retrieving schemas in the database - " + e.getMessage());
        }
        return schemaNameList;
    }

    private String getAllSchemasInTheDatabaseQuery(){
        return "SELECT schema_name FROM information_schema.schemata";
    }

    public Map<String, List<String>> getSchemaNameTableNameListMap(List<String> schemaNameList){
        Map<String, List<String>> schemaNameTableListMap = new HashMap<>();
        for(String schemaName: schemaNameList) {
            List<String> tableNameList = this.getSchemaTableNameList(schemaName);
            schemaNameTableListMap.put(schemaName, tableNameList);
        }
        return schemaNameTableListMap;
    }

    private List<String> getSchemaTableNameList(String schemaName){
        List<String> tableNameList = new ArrayList<>();
        String schemaTableQuery = String.format("SELECT * FROM information_schema.tables WHERE table_schema = '%s'", schemaName);
        ResultWrapper resultWrapper = queryExecutor.executeQuery(schemaTableQuery);
        ResultSet resultSet = resultWrapper.getResultSet();
        try {
            while (resultSet.next()) {
                String tableName = resultSet.getString("table_name");
                tableNameList.add(tableName);
            }
        }catch (SQLException e){
            System.out.println("Error retrieving schemas in the database - " + e.getMessage());
        }
        return tableNameList;
    }

    public List<Relationship> getTableRelationships(String searchTable){
        List<Relationship> relationshipList = new ArrayList<>();
        String schemaTableQuery = String.format("SELECT * FROM %s;", searchTable);
        ResultWrapper resultWrapper = queryExecutor.executeQuery(schemaTableQuery);
        ResultSet resultSet = resultWrapper.getResultSet();
        try {
            while (resultSet.next()) {
                String leftTableName = resultSet.getString("left_table_name");
                String rightTableName = resultSet.getString("right_table_name");
                String type = resultSet.getString("type");
                Table leftTable = null;
                Table rightTable = null;
                var relationship = new Relationship(leftTable, rightTable, type);
                relationshipList.add(relationship);
            }
        }catch (SQLException e){
            System.out.println("Error retrieving schemas in the database - " + e.getMessage());
        }
        return relationshipList;
    }

    public Table getTable(String schemaName, String tableName){
        Table table = null;
        return table;
    }

    public List<Column> getTableColumnList(String schemaName, String tableName){
        List<Column> columnList = new ArrayList<>();
        return columnList;
    }

    public Column getColumn(String tableName, String columnName){
        Column column = null;
        return column;
    }

}