package com.example.springauth;

import com.example.springauth.columns.Column;
import com.example.springauth.columns.ColumnInitializer;
import com.example.springauth.dialects.postgres.DatabaseCredentials;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.relationships.BaseRelationshipGenerator;
import com.example.springauth.relationships.Relationship;
import com.example.springauth.relationships.RelationshipResolver;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaGenerator;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.specialtables.TableOfColumns;
import com.example.springauth.specialtables.TableOfRelationships;
import com.example.springauth.specialtables.TableOfSchemas;
import com.example.springauth.specialtables.TableOfTables;
import com.example.springauth.tables.Table;
import com.example.springauth.tables.TableGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class StandardizationManager {

    private static StandardizationManager standardizationManager = null;

    private StandardizationManager(){
    }

    public static StandardizationManager getStandardizationManager(){
        if (standardizationManager != null)
            return standardizationManager;
        standardizationManager = new StandardizationManager();
        return standardizationManager;
    }

    public void initializeStandardAuthModule(){
        String databaseName = "standardization";
        String jdbcUrl = "jdbc:postgresql://localhost:5432/" + databaseName;
        String username = "postgres";
        String password = "evolve";

        // Initialize the SQL dialect to be used. This impacts everything onwards.
        String sqlDialect = "POSTGRES";
        DatabaseCredentials databaseCredentials = new DatabaseCredentials(jdbcUrl, username, password);
        QueryExecutor queryExecutor = new QueryExecutor(databaseCredentials);


        // Get a name schema object mapping.
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        Map<String, Schema> nameSchemaMap = schemaGenerator.getNameSchemaMap();
        // Get a list of all created schemas as well.
        List<Schema> databaseSchemaList = schemaGenerator.getSchemaList();
        // obtain the two most important schemas
        Schema databaseDocumentationSchema = nameSchemaMap.get(SchemaNameGiver.getDocumentationSchemaName());
        Schema userManagementSchema = nameSchemaMap.get(SchemaNameGiver.getUserManagementSchemaName());

        // Get the initial columns for all modeled entities in the system
        ColumnInitializer colInit = ColumnInitializer.getColumnInitializer(databaseDocumentationSchema, userManagementSchema);
        List<Column> commonColumnList = colInit.getCommonColumnList();


        // Get all tables in a table name table mapping
        Map<String, Table> nameTableMap = getNameTableMap(colInit, nameSchemaMap);

        // Obtain a list of tables alone from the table name table object mapping
        List<Table> universalTableList = TableGenerator.getUniversalTableList(nameTableMap);

        // Obtain a list of relationships from the table name table object mapping
        List<Relationship> relationshipList = getRelationshipList(nameTableMap);

        // Ensure entity integrity and referential integrity for one-to-one and one-to-many relationships
        List<Table> enhancedTableList = ensureEntityAndReferentialIntegrity(relationshipList);

        // New universal table list, always assert that the enhanced table list size is equal to the original table size
        universalTableList = replaceWithEnhancedTables(universalTableList, enhancedTableList);
        universalTableList = addCommonColumns(universalTableList, commonColumnList);
        universalTableList = addCommonProperties(universalTableList, sqlDialect, queryExecutor);

        // Get derived tables from many to many (*:*) relationships
        List<Table> derivedTableList = getDerivedTables(relationshipList);
        derivedTableList = addCommonProperties(derivedTableList, sqlDialect, queryExecutor);
        derivedTableList = addCommonColumns(derivedTableList, commonColumnList);

        // Add the derived tables to the universal table list
        universalTableList.addAll(derivedTableList);

        /* Creating all database objects in their order of relevance */

        // Creating physical schemas in the database
        TableOfSchemas schemaTable = new TableOfSchemas(queryExecutor, sqlDialect, databaseDocumentationSchema);
        schemaTable.createDatabaseSchemas(databaseSchemaList);

        // Create all physical tables in the database.
        // Special tables constitute the tables of schemas, tables of tables, table of relationships and the table of columns
        Table.createTables(universalTableList);

        /* Documenting all database objects after the respective tables have been created */

        // Document all schemas in the database
        schemaTable.documentSchemas(databaseSchemaList);

        // Document all tables in the database
        TableOfTables specialTableOfTables = new TableOfTables(queryExecutor, sqlDialect, databaseDocumentationSchema);
        specialTableOfTables.documentTables(universalTableList);

        // Document all relationships in the database
        TableOfRelationships specialTableOfRelationships = new TableOfRelationships(queryExecutor, sqlDialect, databaseDocumentationSchema);
        specialTableOfRelationships.documentRelationships(relationshipList);

        // Document all columns in the database
        List<Column> allTablesColumnList = getUniversalTableColumnList(universalTableList);
        TableOfColumns tableOfColumns = new TableOfColumns(queryExecutor, sqlDialect, databaseDocumentationSchema);
        tableOfColumns.documentColumns(allTablesColumnList);

        /* Deliverable - 1/2. Obtain database documentation */
        //DatabaseDocumentation databaseDocumentation = new DatabaseDocumentation(sqlDialect, queryExecutor, databaseDocumentationSchema);
        //System.out.println(databaseDocumentation.generateDatabaseDocumentation());
    }

    private static Map<String, Table> getNameTableMap(ColumnInitializer colInit, Map<String, Schema> categorySchemaMap){
        TableGenerator tableGenerator = new TableGenerator(colInit, categorySchemaMap);
        return tableGenerator.getNameTableMap();
    }

    private static List<Table> addCommonColumns(List<Table> tableList, List<Column> commonColumnList){
        List<Table> newTableList = new ArrayList<>();
        for(Table table: tableList){
            Schema schema = table.getSchema() != null ? table.getSchema() : null;
            String tableFQN = "";
            if(!table.getName().contains("."))
                tableFQN = schema != null && !schema.getName().isEmpty() ? (schema.getName() + "." + table.getName()) : table.getName();
            else
                tableFQN = table.getName();
            List<Column> newCommonColumnList = new ArrayList<>();
            for (Column column: commonColumnList){
                try {
                    Column clone = column.clone();
                    clone.setTableName(tableFQN);
                    newCommonColumnList.add(clone);
                }catch (CloneNotSupportedException e){
                    System.out.println(e.getMessage());
                }
            }
            table.setBaseColumnList(newCommonColumnList);
            newTableList.add(table);
        }
        return newTableList;
    }

    private static List<Table> addCommonProperties(List<Table> tableList, String sqlDialect, QueryExecutor queryExecutor){
        List<Table> newTableList = new ArrayList<>();
        for(Table table: tableList){
            table.setSqlDialect(sqlDialect);
            table.setQueryExecutor(queryExecutor);
            newTableList.add(table);
        }
        return newTableList;
    }

    private static List<Column> getUniversalTableColumnList(List<Table> tableList){
        List<Column> allTablesColumnList = new ArrayList<>();
        for(Table table: tableList)
            allTablesColumnList.addAll(table.getUniversalColumnList());
        return allTablesColumnList;
    }

    private static List<Relationship> getRelationshipList(Map<String, Table> nameTableMap){
        BaseRelationshipGenerator baseRelationshipGenerator = new BaseRelationshipGenerator(nameTableMap);
        return baseRelationshipGenerator.getRelationshipList();
    }

    private static List<Table> getDerivedTables(List<Relationship> relationshipList){
        RelationshipResolver relationshipResolver = new RelationshipResolver();
        return relationshipResolver.resolveManyToManyRelationships(relationshipList);
    }

    private static List<Table> ensureEntityAndReferentialIntegrity(List<Relationship> relationshipList){
        RelationshipResolver relationshipResolver = new RelationshipResolver();
        return relationshipResolver.ensureEntityAndReferentialIntegrity(relationshipList);
    }

    private static List<Table> replaceWithEnhancedTables(List<Table> tableList, List<Table> enhancedTableList){
        List<Table> newTableList = new ArrayList<>(enhancedTableList);
        for (Table table: tableList)
            if (!newTableList.contains(table))
                newTableList.add(table);
        return newTableList;
    }
}
