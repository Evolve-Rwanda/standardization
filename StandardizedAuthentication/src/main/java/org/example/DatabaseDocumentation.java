package org.example;

import org.example.columns.Column;
import org.example.dialects.postgres.PostgresDialect;
import org.example.dialects.postgres.QueryExecutor;
import org.example.schemas.Schema;
import org.example.specialtables.SpecialTableNameGiver;
import org.example.tables.Table;
import org.example.relationships.Relationship;
import java.util.*;




public class DatabaseDocumentation {



    private final String sqlDialect;
    private final Schema documentationSchema;
    private final QueryExecutor queryExecutor;


    public DatabaseDocumentation(String sqlDialect, QueryExecutor queryExecutor, Schema documentationSchema){
        this.sqlDialect = sqlDialect;
        this.queryExecutor = queryExecutor;
        this.documentationSchema = documentationSchema;
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

        // select all tables in the database
        documentationBuilder.append("\n\n<br /><p>The database will have the following pre modelled tables in an attempt to standardize software modules common many software development projects.</p>");
        documentationBuilder.append("<h3>Table details</h3>");
        String tableOfTables = SpecialTableNameGiver.getTableOfTablesName();
        List<Table> tableList = getTableList(tableOfTables);
        List<List<String>> tableDetailsListOfList = new ArrayList<>();
        List<String> tableDetailsHeaderList = new ArrayList<>();
        tableDetailsHeaderList.add("Table name");
        tableDetailsHeaderList.add("Table Fully Qualified Name (FQN)");
        tableDetailsHeaderList.add("Description");
        for(Table table: tableList) {
            List<String> detailsList = new ArrayList<>();
            detailsList.add(table.getName());
            detailsList.add(table.getFullyQualifiedName());
            detailsList.add(table.getDescription());
            tableDetailsListOfList.add(detailsList);
        }
        String tablesMarkup = getHTMLTableMarkup(tableDetailsHeaderList, tableDetailsListOfList);
        documentationBuilder.append(tablesMarkup);

        // select all tables' columns in the database
        documentationBuilder.append("\n<br /><p>Each of the tables above have the following columns used to maintain data about their corresponding entities</p>");
        List<String> columnDetailsHeaderList = new ArrayList<>();
        columnDetailsHeaderList.add("Table name");
        columnDetailsHeaderList.add("Column number");
        columnDetailsHeaderList.add("Column name");
        columnDetailsHeaderList.add("Column data type");
        columnDetailsHeaderList.add("Precision");
        columnDetailsHeaderList.add("Scale");
        columnDetailsHeaderList.add("Default value");
        columnDetailsHeaderList.add("Is nullable");
        columnDetailsHeaderList.add("Is PK");
        columnDetailsHeaderList.add("Is Fk");
        columnDetailsHeaderList.add("Reference table Name (FQN)");
        columnDetailsHeaderList.add("Reference column Name");
        columnDetailsHeaderList.add("On update action");
        columnDetailsHeaderList.add("On delete action");
        columnDetailsHeaderList.add("Is a fact based column");
        columnDetailsHeaderList.add("Is encrypted");
        columnDetailsHeaderList.add("Is indexed");
        columnDetailsHeaderList.add("Description");
        for(Table table: tableList) {
            documentationBuilder.append("<h3>").append(table.getFullyQualifiedName()).append("</h3>");
            List<Column> tableCOlumnList = table.getUniversalColumnList();
            List<List<String>> columnDetailsRowList = new ArrayList<>();
            for(Column c: tableCOlumnList) {
                List<String> columnDetailsRow = new ArrayList<>();
                columnDetailsRow.add(c.getTableName());
                columnDetailsRow.add(c.getNumber() + "");
                columnDetailsRow.add(c.getName());
                columnDetailsRow.add(c.getDataType());
                columnDetailsRow.add(c.getPrecision() + "");
                columnDetailsRow.add(c.getScale() + "");
                columnDetailsRow.add(c.getDefaultValue());
                columnDetailsRow.add(c.getIsNullable());
                columnDetailsRow.add(c.getIsPK());
                columnDetailsRow.add(c.getIsFK());
                columnDetailsRow.add(c.getReferenceTableName());
                columnDetailsRow.add(c.getReferenceColumnName());
                columnDetailsRow.add(c.getOnUpdateAction());
                columnDetailsRow.add(c.getOnDeleteAction());
                columnDetailsRow.add(c.getIsAFactBasedColumn());
                columnDetailsRow.add(c.getIsEncrypted());
                columnDetailsRow.add(c.getIsIndexed());
                columnDetailsRow.add(c.getDescription());
                columnDetailsRowList.add(columnDetailsRow);
            }
            String tableColumnsMarkup = getHTMLTableMarkup(columnDetailsHeaderList, columnDetailsRowList);
            documentationBuilder.append(tableColumnsMarkup);
        }

        // select all relationships between the different tables
        documentationBuilder.append("\n<br /><br /><p>The tables have the following relationships between them</p>");
        documentationBuilder.append("<h3>Relationship details</h3>");
        List<String> relationshipDetailsHeaderList = new ArrayList<>();
        relationshipDetailsHeaderList.add("Left table name FQN");
        relationshipDetailsHeaderList.add("Right table name FQN");
        relationshipDetailsHeaderList.add("Type");
        relationshipDetailsHeaderList.add("Description");
        List<List<String>> relationshipListOfList = new ArrayList<>();
        String tablesOfRelationships = SpecialTableNameGiver.getTableOfRelationshipsName();
        List<Relationship> relationshipList = getTableRelationships(tablesOfRelationships);

        Set<Table> tableSet = new HashSet<>();

        for (Relationship r: relationshipList) {
            Table leftTable = r.getLeftTable();
            Table righTable = r.getRightTable();
            String type = r.getType();
            String description = r.getDescription();
            List<String> relationshipRowList = new ArrayList<>();
            relationshipRowList.add(leftTable.getFullyQualifiedName());
            relationshipRowList.add(righTable.getFullyQualifiedName());
            relationshipRowList.add(type);
            relationshipRowList.add(description);
            relationshipListOfList.add(relationshipRowList);
            tableSet.add(leftTable);
            tableSet.add(righTable);
        }
        String relationshipMarkup = getHTMLTableMarkup(relationshipDetailsHeaderList, relationshipListOfList);
        documentationBuilder.append(relationshipMarkup);


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

    private String getHTMLTableMarkup(List<String> headingsList, List<List<String>> valueListofList){
        StringBuilder tableMarkupBuilder = new StringBuilder();
        tableMarkupBuilder.append("<table>");
        tableMarkupBuilder.append("<tr>");
        tableMarkupBuilder.append("<td>&nbsp;</td>");
        for(String heading: headingsList){
            tableMarkupBuilder.append("<td>");
            tableMarkupBuilder.append(heading);
            tableMarkupBuilder.append("</td>");
        }
        tableMarkupBuilder.append("</tr>");
        int count = 1;
        for(List<String> valueList: valueListofList) {
            tableMarkupBuilder.append("<tr>");
            tableMarkupBuilder.append("<td>");
            tableMarkupBuilder.append(count);
            tableMarkupBuilder.append("</td>");
            for(String value: valueList){
                tableMarkupBuilder.append("<td>");
                tableMarkupBuilder.append(value);
                tableMarkupBuilder.append("</td>");
            }
            tableMarkupBuilder.append("</tr>");
            count++;
        }
        tableMarkupBuilder.append("</table>");
        return tableMarkupBuilder.toString();
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
