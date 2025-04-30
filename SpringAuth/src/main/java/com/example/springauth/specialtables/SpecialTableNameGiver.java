package com.example.springauth.specialtables;


import java.util.ArrayList;
import java.util.List;


public class SpecialTableNameGiver {


    private static final String tableOfSchemasName = "table_of_schemas";
    private static final String tableOfTablesName = "table_of_tables";
    private static final String tableOfRelationshipsName = "table_of_relationships";
    private static final String tableOfColumnsName = "table_of_columns";
    private static final List<String> specialTableNameList;

    static {
        specialTableNameList = new ArrayList<>();
        specialTableNameList.add(tableOfSchemasName);
        specialTableNameList.add(tableOfTablesName);
        specialTableNameList.add(tableOfRelationshipsName);
        specialTableNameList.add(tableOfColumnsName);
    }

    public static String getTableOfSchemasName(){
        return tableOfSchemasName;
    }

    public static String getTableOfTablesName(){
        return tableOfTablesName;
    }

    public static String getTableOfRelationshipsName(){
        return tableOfRelationshipsName;
    }

    public static String getTableOfColumnsName(){
        return tableOfColumnsName;
    }

    public static List<String> getSpecialTableNameList(){
        return specialTableNameList;
    }
}
