package org.example.specialtables;


public class SpecialTableName {


    private static final String tableOfSchemas = "table_of_schemas";
    private static final String tableOfTables = "table_of_tables";
    private static final String tableOfRelationships = "table_of_relationships";
    private static final String tableOfColumns = "table_of_columns";


    public static String getTableOfSchemas(){
        return tableOfSchemas;
    }

    public static String getTableOfTables(){
        return tableOfTables;
    }

    public static String getTableOfRelationships(){
        return tableOfRelationships;
    }

    public static String getTableOfColumns(){
        return tableOfColumns;
    }
}
