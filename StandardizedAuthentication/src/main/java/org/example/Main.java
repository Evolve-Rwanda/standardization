package org.example;

import java.util.*;


public class Main {


    public static void main(String[] args) {

        String databaseName = "standardization";
        String jdbcUrl = "jdbc:postgresql://localhost:5432/" + databaseName;
        String username = "postgres";
        String password = "evolve";

        // Initialize the SQL dialect to be used. This impacts everything onwards.
        String sqlDialect = "POSTGRES";
        DatabaseCredentials databaseCredentials = new DatabaseCredentials(jdbcUrl, username, password);
        QueryExecutor queryExecutor = new QueryExecutor(databaseCredentials);


        // Create the two most important schemas
        Schema userManagementSchema = new Schema("UM", "Concerned with user management tables", DateTime.getTimeStamp(), "");
        Schema databaseDocumentationSchema = new Schema("DB_DOC", "Concerned with documenting the database objects", DateTime.getTimeStamp(), "");
        // Keep the schemas in a hashmap
        Map<String, Schema> categorySchemaMap = new HashMap<>();
        categorySchemaMap.put("documentation", databaseDocumentationSchema);
        categorySchemaMap.put("user_management", userManagementSchema);
        // Get a list of all created schemas as well.
        List<Schema> databaseSchemaList = Schema.getSchemaList();

        // Get the initial columns for all modeled entities in the system
        ColumnInitializer colInit = ColumnInitializer.getColumnInitializer();
        List<Column> commonColumnList = colInit.getCommonColumnList();


        // Get all tables in a table name table mapping
        Map<String, Table> nameTableMap = getNameTableMap(colInit, categorySchemaMap);

        // Obtain a list of tables alone from the table name table object mapping
        List<Table> universalTableList = getuniversalTableList(nameTableMap);

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
    }

    private static Map<String, Table> getNameTableMap(ColumnInitializer colInit, Map<String, Schema> categorySchemaMap){
        Map<String, Table> nameTableMap = new HashMap<>();

        var tableOfSchemas = new Table("table_of_schemas", colInit.getTableOfSchemasColumnList());
        tableOfSchemas.setSchema(categorySchemaMap.get("documentation"));
        nameTableMap.put(tableOfSchemas.getName(), tableOfSchemas);

        var tableOfTables = new Table("table_of_tables", colInit.getTableOfTablesColumnList());
        tableOfTables.setSchema(categorySchemaMap.get("documentation"));
        nameTableMap.put(tableOfTables.getName(), tableOfTables);

        var tableOfRelationships = new Table("table_of_relationships", colInit.getTableOfRelationshipsColumnList());
        tableOfRelationships.setSchema(categorySchemaMap.get("documentation"));
        nameTableMap.put(tableOfRelationships.getName(), tableOfRelationships);

        var tableOfColumns = new Table("table_of_columns", colInit.getTableOfColumnsColumnList());
        tableOfColumns.setSchema(categorySchemaMap.get("documentation"));
        nameTableMap.put(tableOfColumns.getName(), tableOfColumns);

        var userTable = new UserTable("user", colInit.getUserColumnList());
        userTable.setSchema(categorySchemaMap.get("user_management"));
        nameTableMap.put(userTable.getName(), userTable);

        var involvedEntityTable = new InvolvedEntityTable("involved_entity", colInit.getInvolvedEntityColumnList());
        involvedEntityTable.setSchema(categorySchemaMap.get("user_management"));
        nameTableMap.put(involvedEntityTable.getName(), involvedEntityTable);

        var roleTable = new RoleTable("role", colInit.getRoleColumnList());
        roleTable.setSchema(categorySchemaMap.get("user_management"));
        nameTableMap.put(roleTable.getName(), roleTable);

        var privilegeTable = new PrivilegeTable("privilege", colInit.getPrivilegeColumnList());
        privilegeTable.setSchema(categorySchemaMap.get("user_management"));
        nameTableMap.put(privilegeTable.getName(), privilegeTable);

        var contactTable = new ContactTable("contact", colInit.getContactColumnList());
        contactTable.setSchema(categorySchemaMap.get("user_management"));
        nameTableMap.put(contactTable.getName(), contactTable);

        var contactTypeTable = new ContactTypeTable("contact_type", colInit.getContactTypeColumnList());
        contactTypeTable.setSchema(categorySchemaMap.get("user_management"));
        nameTableMap.put(contactTypeTable.getName(), contactTypeTable);

        var addressTable = new AddressTable("address", colInit.getAddressColumnList());
        addressTable.setSchema(categorySchemaMap.get("user_management"));
        nameTableMap.put(addressTable.getName(), addressTable);

        var authHistTable = new AuthenticationHistoryTable("authentication_history", colInit.getAuthHistoryColumnList());
        authHistTable.setSchema(categorySchemaMap.get("user_management"));
        nameTableMap.put(authHistTable.getName(), authHistTable);

        var authMetadataTable = new AuthenticationMetadataTable("authentication_metadata", colInit.getAuthMetadataColumnList());
        authMetadataTable.setSchema(categorySchemaMap.get("user_management"));
        nameTableMap.put(authMetadataTable.getName(), authMetadataTable);

        return nameTableMap;
    }

    private static List<Table> getuniversalTableList(Map<String, Table> nameTableMap){
        List<Table> universalTableList = new ArrayList<>();
        for (String tableName: nameTableMap.keySet()){
            Table nonSpecialTable = nameTableMap.get(tableName);
            universalTableList.add(nonSpecialTable);
        }
        return universalTableList;
    }

    private static List<Table> addCommonColumns(List<Table> tableList, List<Column> commonColumnList){
        List<Table> newTableList = new ArrayList<>();
        for(Table table: tableList){
            List<Column> newCommonColumnList = new ArrayList<>();
            for (Column column: commonColumnList){
                try {
                    Column clone = column.clone();
                    clone.setTableName(table.getName().replace("\"", ""));
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
        // Table relationships
        Table userTable = nameTableMap.get("user"), roleTable = nameTableMap.get("role");
        Table involvedEntityTable = nameTableMap.get("involved_entity"), addressTable = nameTableMap.get("address");
        Table contactTable = nameTableMap.get("contact"), contactTypeTable = nameTableMap.get("contact_type");
        Table authHistTable = nameTableMap.get("authentication_history");
        Table authMetadataTable = nameTableMap.get("authentication_metadata");
        Table privilegeTable = nameTableMap.get("privilege");
        new Relationship(userTable, roleTable, "*:*");
        new Relationship(userTable, involvedEntityTable, "*:*");
        new Relationship(userTable, addressTable, "*:*");
        new Relationship(userTable, contactTable, "*:*");
        new Relationship(contactTypeTable, contactTable, "1:*");
        new Relationship(userTable, authHistTable, "1:*");
        new Relationship(authHistTable, authMetadataTable, "1:1");
        new Relationship(roleTable, privilegeTable, "1:*");
        new Relationship(involvedEntityTable, contactTable, "*:*");
        new Relationship(involvedEntityTable, addressTable, "*:*");

        return Relationship.getRelationshipList();
    }

    private static List<Table> getDerivedTables(List<Relationship> relationshipList){
        List<Table> derivedTableList = new ArrayList<>();
        for(Relationship relationship: relationshipList){
            Table leftTable = relationship.getLeftTable();
            List<Column> leftTablePrimaryKeyList = leftTable.getPrimaryKeyList();
            Table rightTable = relationship.getRightTable();
            List<Column> rightTablePrimaryKeyList = rightTable.getPrimaryKeyList();
            String type = relationship.getType().trim();
            if(type.equalsIgnoreCase("*:*")){
                List<Column> derivedTableColumnList = new ArrayList<>();
                List<Column> columnList = new ArrayList<>();
                columnList.addAll(leftTablePrimaryKeyList);
                columnList.addAll(rightTablePrimaryKeyList);
                String derivedTableName = (leftTable.getName() + "_" + rightTable.getName() + "_map").replace("\"", "");
                int n = 1;
                for (Column column: columnList){
                    try {
                        String tableName = leftTable.getUniversalColumnList().contains(column) ? leftTable.getName() : rightTable.getName();
                        tableName = tableName.replace("\"", "");
                        String cloneName = (tableName + "_" + column.getName()).replace("\"", "");
                        Column clone = column.clone();
                        clone.setTableName(derivedTableName);
                        clone.setName(cloneName);
                        clone.setIsPK("true");
                        clone.setIsFK("true");
                        clone.setReferenceColumnName(column.getName());
                        clone.setReferenceTableName(tableName);
                        clone.setNumber(n);
                        derivedTableColumnList.add(clone);
                        n++;
                    }catch (CloneNotSupportedException e){
                        System.out.println(e.getMessage());
                    }
                }
                Table derivedTable = new DerivedTable(derivedTableName, derivedTableColumnList);
                if (leftTable.getSchema().equals(rightTable.getSchema()))
                    derivedTable.setSchema(leftTable.getSchema());
                derivedTableList.add(derivedTable);
            }
        }
        return derivedTableList;
    }

    private static List<Table> ensureEntityAndReferentialIntegrity(List<Relationship> relationshipList){
        List<Table> enhancedTableList = new ArrayList<>();
        for(Relationship relationship: relationshipList){
            Table strongTable = relationship.getLeftTable();
            List<Column> strongTablePrimaryKeyList = strongTable.getPrimaryKeyList();
            Table weakTable = relationship.getRightTable();
            String type = relationship.getType().trim();
            if (type.equalsIgnoreCase("1:*")
                 || type.equalsIgnoreCase("1:1")
               ) {
                for(Column column: strongTablePrimaryKeyList){
                    try {
                        Column clone  = column.clone();
                        String parentTableName = strongTable.getName();
                        clone.setTableName(weakTable.getName());
                        String newColumnName = (parentTableName + "_" + clone.getName()).replace("\"", "");
                        clone.setName(newColumnName);
                        clone.setReferenceColumnName(column.getName());
                        clone.setReferenceTableName(parentTableName);
                        clone.setIsPK("false");
                        clone.setIsFK("true");
                        weakTable.addColumn(clone);
                    }catch (CloneNotSupportedException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            if (!enhancedTableList.contains(weakTable))
                enhancedTableList.add(weakTable);
            if (!enhancedTableList.contains(strongTable))
                enhancedTableList.add(strongTable);
        }
        return enhancedTableList;
    }

    private static List<Table> replaceWithEnhancedTables(List<Table> tableList, List<Table> enhancedTableList){
        List<Table> newTableList = new ArrayList<>(enhancedTableList);
        for (Table table: tableList)
            if (!newTableList.contains(table))
                newTableList.add(table);
        return newTableList;
    }

}