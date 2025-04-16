package org.example;

import java.util.*;



public class DatabaseDocumentation {


    private final String sqlDialect;
    private final Schema documentationSchema;
    private final QueryExecutor queryExecutor;
    private String tableOfSchemas;
    private String tableOfTables;
    private String tablesOfRelationships;
    private String tableOfColumns;


    public DatabaseDocumentation(String sqlDialect, QueryExecutor queryExecutor, Schema documentationSchema){
        this.sqlDialect = sqlDialect;
        this.queryExecutor = queryExecutor;
        this.documentationSchema = documentationSchema;
    }

    public void setTableOfColumns(String tableOfColumns) {
        this.tableOfColumns = tableOfColumns;
    }

    public void setTableOfSchemas(String tableOfSchemas) {
        this.tableOfSchemas = tableOfSchemas;
    }

    public void setTablesOfRelationships(String tablesOfRelationships) {
        this.tablesOfRelationships = tablesOfRelationships;
    }

    public void setTableOfTables(String tablesOfTables) {
        this.tableOfTables = tablesOfTables;
    }

    // 1. Tell the developer of database administrator about the different schemas in the database
    //    This list can be obtained from the table of schemas in the provided schema
    // 2. For Each schema, list the strong tables first.
    // 3. List the relationships between all the tables in the database and all information to describe the relationships
    // 4. List all the derived entities as a result of the relationships.
    // 5. List all the information that is used to explain the columns in a particular table.
    // 6. For each column, list all the columns in it along with all the relevant information and documentation
    // 7. Aggregate all the above into a single JSON object to be displayed on the front end.

    public String generateDatabaseDocumentation(){
        StringBuilder documentationBuilder = new StringBuilder();
        documentationBuilder.append("<p>The database documentation information for this application can be found in the \"");
        documentationBuilder.append(documentationSchema.getName());
        documentationBuilder.append("\" schema from whence it can be generated in a preferable way.</p> ");
        documentationBuilder.append(
                "<p>This documentation contains information about the different different " +
                        "schemas, tables, relationships between the different tables, and the columns that belong to each and every table.</p> "
        );
        documentationBuilder.append("The different schemas in the database include - ");
        List<String> allSchemaNameList = this.getDatabaseSchemaNameList();
        documentationBuilder.append(this.getHTMLOrderedList(allSchemaNameList));
        documentationBuilder.append(
                "However, the following schemas are not of interest or relevance to documenting important objects " +
                "relevant to the modelled application objects of the system"
        );
        List<String> databaseSysSchemaList = getDatabaseSysSchemaNameList();
        List<String> focusSchemaNameList = this.getListDifference(allSchemaNameList, databaseSysSchemaList);
        documentationBuilder.append(this.getHTMLOrderedList(databaseSysSchemaList));

        // select tables in all the relevant schemas
        Map<String, List<String>> schemaTableListMap = getSchemaNameTableNameListMap(focusSchemaNameList);
        for (String s: schemaTableListMap.keySet()){
            documentationBuilder.append(
                    String.format("The <i>%s</i> schema manages the following list of tables", s)
            );
            documentationBuilder.append(this.getHTMLOrderedList(schemaTableListMap.get(s)));
        }

        // select all relationships between the different tables
        documentationBuilder.append("\n<br />The tables have the following relationships between them");
        List<Relationship> relationshipList = getTableRelationships(this.tablesOfRelationships);
        List<String> relationshipStringList = new ArrayList<>();
        Set<Table> tableSet = new HashSet<>();
        for (Relationship r: relationshipList) {
            Table leftTable = r.getLeftTable();
            Table righTable = r.getRightTable();
            String relationshipString = leftTable.getName() + " and " + righTable.getName();
            relationshipStringList.add(relationshipString);
            tableSet.add(leftTable);
            tableSet.add(righTable);
        }
        List<Table> tableList = getTableList(this.tableOfTables);
        for(Table table: tableList)
            System.out.println(table + " - " + table.getUniversalColumnList());

        documentationBuilder.append(this.getHTMLOrderedList(relationshipStringList));

        return documentationBuilder.toString();
    }


    private String getHTMLOrderedList(List<String> stringList){
        StringBuilder orderedListBuilder = new StringBuilder();
        orderedListBuilder.append("<ol type=\"number\">");
        for(String item: stringList)
            orderedListBuilder.append("<li>").append(item).append("</li>");
        orderedListBuilder.append("</ol>");
        return orderedListBuilder.toString();
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

}
