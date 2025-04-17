package org.example.dialects.postgres;

import org.example.dialects.SQLDialect;
import org.example.specialtables.SpecialTable;
import org.example.tables.Table;
import org.example.schemas.Schema;
import org.example.relationships.Relationship;
import org.example.columns.Column;
import org.example.utilities.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



public class PostgresDialect extends SQLDialect {


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

    protected String name = "POSTGRES";
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
            throw new NullPointerException("Cannot create a schema from an empty or null list of schemas");
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
            String leftTableName = relationship.getLeftTable().getName();
            Schema leftTableSchema = relationship.getLeftTable().getSchema();
            String leftTableFQN = leftTableSchema != null && !leftTableSchema.getName().isEmpty() ? (leftTableSchema.getName() + "." + leftTableName) : leftTableName;
            String rightTableName = relationship.getRightTable().getName();
            Schema rightTableSchema = relationship.getRightTable().getSchema();
            String rightTableFQN = rightTableSchema != null && !rightTableSchema.getName().isEmpty() ? (rightTableSchema.getName() + "." + rightTableName) : rightTableName;
            fieldNameValueMap.put("left_table_name", "'" + leftTableFQN + "'");
            fieldNameValueMap.put("right_table_name", "'" + rightTableFQN + "'");
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
        Schema leftTableSchema = relationship.getLeftTable().getSchema();
        String leftTableFQN = leftTableSchema != null && !leftTableSchema.getName().isEmpty() ? (leftTableSchema.getName() + "." + leftTableName) : leftTableName;
        String rightTableName = relationship.getRightTable().getName();
        Schema rightTableSchema = relationship.getRightTable().getSchema();
        String rightTableFQN = rightTableSchema != null && !rightTableSchema.getName().isEmpty() ? (rightTableSchema.getName() + "." + rightTableName) : rightTableName;
        String type = relationship.getType();
        String createdAt = DateTime.getTimeStamp();
        String columnValues = "'" + leftTableFQN + "', '" + rightTableFQN + "', '" + type + "', '" + createdAt + "'";
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
        }finally {
            queryExecutor.closeResources();
        }
        return tableNameList;
    }

    public List<Relationship> getTableRelationships(String searchTable){
        List<Relationship> relationshipList = new ArrayList<>();
        String schemaName = schema != null ? schema.getName() : "";
        String prefix = !schemaName.isEmpty() ? schemaName + "." : schemaName;
        String relationshipTableQuery = String.format("SELECT * FROM %s;", (prefix + searchTable));
        ResultWrapper resultWrapper = queryExecutor.executeQuery(relationshipTableQuery);
        ResultSet resultSet = resultWrapper.getResultSet();
        try {
            while (resultSet.next()) {
                String leftTableName = resultSet.getString("left_table_name");
                String rightTableName = resultSet.getString("right_table_name");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                Table leftTable = this.getTable(schemaName, leftTableName);
                Table rightTable = this.getTable(schemaName, rightTableName);
                var relationship = new Relationship(leftTable, rightTable, type, description);
                relationshipList.add(relationship);
            }
        }catch (SQLException e){
            System.out.println("Error retrieving relationships in the database - " + e.getMessage());
        }finally {
            queryExecutor.closeResources();
        }
        return relationshipList;
    }

    public List<Table> getTableList(String searchTable){
        List<Table> tableList = new ArrayList<>();
        String schemaName = schema != null ? schema.getName() : "";
        String prefix = !schemaName.isEmpty() ? schemaName + "." : schemaName;
        String tableQuery = String.format("SELECT * FROM %s;", (prefix + searchTable));
        ResultWrapper resultWrapper = queryExecutor.executeQuery(tableQuery);
        ResultSet resultSet = resultWrapper.getResultSet();
        Set<String> tableNameSet = new HashSet<>();
        try {
            while (resultSet.next()) {
                String tableName = resultSet.getString("table_name");
                String tableSchemaName = resultSet.getString("schema_name");
                String description = resultSet.getString("description");
                String tableFQN = tableSchemaName != null && !tableSchemaName.isEmpty() ? tableSchemaName + "." + tableName : tableName;
                if(tableNameSet.contains(tableFQN))
                    continue;
                tableNameSet.add(tableFQN);
                Table table = this.getTable(schemaName, tableFQN);
                table.setDescription(description);
                tableList.add(table);
            }
        }catch (SQLException e){
            System.out.println("Error retrieving tables in the database - " + e.getMessage());
        }finally {
            queryExecutor.closeResources();
        }
        return tableList;
    }

    private Table getTable(String schemaName, String tableName){
        Table table = null;
        List<Column> columnList = this.getTableColumnList(schemaName, tableName);
        System.out.println(documentingTableName + " - " + tableName + " - " + columnList.size());
        table = new Table(tableName, columnList);
        table.setSqlDialect(this.name);
        table.setQueryExecutor(this.queryExecutor);
        return table;
    }

    private List<Column> getTableColumnList(String schemaName, String tableName){
        List<Column> columnList = new ArrayList<>();
        String searchTable = "table_of_columns";
        String searchTableSchema = schema!= null ? schema.getName() : "";
        String searchTableFQN = !searchTableSchema.isEmpty() ? searchTableSchema + "." + searchTable : searchTable;
        String query = String.format("SELECT * from %s WHERE table_name = '%s';", searchTableFQN, tableName);

        ResultWrapper resultWrapper = queryExecutor.executeQuery(query);
        ResultSet resultSet = resultWrapper.getResultSet();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("column_id");
                String columnTableName = resultSet.getString("table_name");
                int number = resultSet.getInt("column_number");
                String name = resultSet.getString("column_name");
                String dataType = resultSet.getString("column_data_type");
                int precision = resultSet.getInt("precision");
                int scale = resultSet.getInt("scale");
                String defaultValue = resultSet.getString("default_value");
                boolean isNullable = resultSet.getString("is_nullable").equalsIgnoreCase("true");
                boolean isPK = resultSet.getString("is_pk").equalsIgnoreCase("true");
                boolean isFK = resultSet.getString("is_fk").equalsIgnoreCase("true");
                String referenceTableName = resultSet.getString("reference_table_name");
                String referenceColumnName = resultSet.getString("reference_column_name");
                String onUpdateAction = resultSet.getString("on_update_action");
                String onDeleteAction = resultSet.getString("on_delete_action");
                boolean isAFactBasedColumn = resultSet.getString("is_a_fact_based_column").equalsIgnoreCase("true");
                boolean isEncrypted = resultSet.getString("is_encrypted").equalsIgnoreCase("true");
                boolean isIndexed = resultSet.getString("is_indexed").equalsIgnoreCase("true");
                String description = resultSet.getString("description");
                String createdAt = resultSet.getString("created_at");
                String deletedAt = resultSet.getString("deleted_at");
                String lastUpdatedAt = resultSet.getString("last_updated_at");
                var column = new Column(tableName, name, number, dataType, precision, scale, defaultValue, isNullable, isAFactBasedColumn, isEncrypted, isPK, isFK, isIndexed, referenceTableName, referenceColumnName, onUpdateAction, onDeleteAction, description, createdAt, deletedAt);
                columnList.add(column);
            }
        }catch (SQLException e){
            System.out.println("Error retrieving table columns from the database table - " + schemaName + "." + tableName + e.getMessage());
        }finally {
            queryExecutor.closeResources();
        }
        return columnList;
    }

}