package com.example.springauth.documentation;

import com.example.springauth.columns.Column;
import com.example.springauth.dialects.postgres.PostgresDialect;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.models.utility.ColumnMarkupElementModel;
import com.example.springauth.models.utility.ColumnValueOptionModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.specialtables.SpecialTableNameGiver;
import com.example.springauth.tables.Table;
import com.example.springauth.relationships.Relationship;
import java.util.*;




public class DatabaseDocumentation {



    private final String sqlDialect;
    private final Schema documentationSchema;
    private final QueryExecutor queryExecutor;
    private List<Schema> schemaList;
    private List<String> schemaNameList;
    private List<Table> tableList;
    private Map<Schema, List<Table>> schemaTableMap;
    private Map<String, List<String>> schemaNameTableNameMap;
    private List<Column> columnList;
    private List<Relationship> relationshipList;


    public DatabaseDocumentation(String sqlDialect, QueryExecutor queryExecutor, Schema documentationSchema){
        this.sqlDialect = sqlDialect;
        this.queryExecutor = queryExecutor;
        this.documentationSchema = documentationSchema;
    }

    public Schema getDocumentationSchema() {
        return documentationSchema;
    }

    public List<Schema> getSchemaList() {
        return schemaList;
    }

    public List<String> getSchemaNameList() {
        return schemaNameList;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public Map<String, List<String>> getSchemaNameTableNameMap() {
        return schemaNameTableNameMap;
    }

    public Map<Schema, List<Table>> getSchemaTableMap() {
        return schemaTableMap;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public List<Relationship> getRelationshipList() {
        return relationshipList;
    }

    // 1. Tell the developer or database administrator about the different schemas in the database
    //    This list can be obtained from the table of schemas in the provided schema
    // 2. For Each schema, list the strong tables first.
    // 3. List the relationships between all the tables in the database and all information to describe the relationships
    // 4. List all the derived entities as a result of the relationships.
    // 5. List all the information that is used to explain the columns in a particular table.
    // 6. For each column, list all the columns in it along with all the relevant information and documentation
    // 7. Aggregate all the above into a single JSON object to be displayed on the front end.

    public void generateDatabaseDocumentation(){
        List<Schema> schemaList = this.getDatabaseSchemaList();
        this.schemaList = schemaList;
        this.schemaNameList = this.getDatabaseSchemaNameList();
        List<String> allSchemaNameList = this.getDatabaseSchemaNameList();
        List<String> databaseSysSchemaList = getDatabaseSysSchemaNameList();
        List<String> focusSchemaNameList = this.getListDifference(allSchemaNameList, databaseSysSchemaList);

        // select tables in all the relevant schemas
        Map<String, List<String>> schemaTableListMap = getSchemaNameTableNameListMap(focusSchemaNameList);
        this.schemaNameTableNameMap = schemaTableListMap;

        // select all tables in the database
        String tableOfTables = SpecialTableNameGiver.getTableOfTablesName();
        this.tableList = getTableList(tableOfTables);
        this.relationshipList = this.getTableRelationships(SpecialTableNameGiver.getTableOfRelationshipsName());

        // Select all registered column option values in the database for all registered applicable columns
        //String columnOptionValuesTable = SpecialTableNameGiver.getTableOfColumnValueOptionsName();
        //List<ColumnValueOptionModel> columnValueOptionModelList = getColumnValueOptionModelList(columnOptionValuesTable);

        // Select all columns' form data capturing elements/tag information for all columns for which the data has been provided
        //getColumnMarkupElementModelList();

    }

    private List<Schema> getDatabaseSchemaList(){
        List<Schema> schemaList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor);
            schemaList = postgresDialect.getDatabaseSchemaList();
        }
        return schemaList;
    }

    private List<String> getDatabaseSchemaNameList(){
        List<String> schemaNameList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor);
            schemaNameList = postgresDialect.getDatabaseSchemaNameList();
        }
        return schemaNameList;
    }

    private List<String> getDatabaseSysSchemaNameList(){
        List<String> schemaNameList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor);
            schemaNameList = postgresDialect.getDatabaseSysSchemaList();
        }
        return schemaNameList;
    }

    private Map<String, List<String>> getSchemaNameTableNameListMap(List<String> schemaNameList){
        Map<String, List<String>> schemaTableListMap = new HashMap<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor);
            schemaTableListMap = postgresDialect.getSchemaNameTableNameListMap(schemaNameList);
        }
        return schemaTableListMap;
    }

    private List<String> getListDifference(List<String> firstStringList, List<String> secondStringList){
        List<String> differenceList = new ArrayList<>();
        for(String s: firstStringList){
            if(!secondStringList.contains(s))
                differenceList.add(s);
        }
        return differenceList;
    }

    private List<Relationship> getTableRelationships(String searchTable){
        List<Relationship> relationshipList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, searchTable, documentationSchema);
            relationshipList = postgresDialect.getTableRelationships(searchTable);
        }
        return relationshipList;
    }

    private List<Table> getTableList(String searchTable){
        List<Table> tableList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, searchTable, documentationSchema);
            tableList = postgresDialect.getTableList(searchTable);
        }
        return tableList;
    }

    public List<ColumnValueOptionModel> getColumnValueOptionModelList(){
        // Select all registered column option values in the database for all registered applicable columns
        String columnOptionValuesTable = SpecialTableNameGiver.getTableOfColumnValueOptionsName();
        return getColumnValueOptionModelList(columnOptionValuesTable);
    }

    private List<ColumnValueOptionModel> getColumnValueOptionModelList(String searchTable){
        List<ColumnValueOptionModel> columnValueOptionModelList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, searchTable, documentationSchema);
            columnValueOptionModelList = postgresDialect.getColumnValueOptionModelList(searchTable);
        }
        return columnValueOptionModelList;
    }

    public List<ColumnMarkupElementModel> getColumnMarkupElementModelList(){
        String columnMarkupElementTableName = SpecialTableNameGiver.getTableOfColumnInputMarkupElementsName();
        return getColumnMarkupElementModelList(columnMarkupElementTableName);
    }

    private List<ColumnMarkupElementModel> getColumnMarkupElementModelList(String searchTable){
        List<ColumnMarkupElementModel> columnMarkupElementModelList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, searchTable, documentationSchema);
            columnMarkupElementModelList = postgresDialect.getColumnMarkupElementModelList(searchTable);
        }
        return columnMarkupElementModelList;
    }

}
