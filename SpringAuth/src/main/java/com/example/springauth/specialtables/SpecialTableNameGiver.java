package com.example.springauth.specialtables;


import java.util.ArrayList;
import java.util.List;


public class SpecialTableNameGiver {


    private static final String tableOfSchemasName = "table_of_schemas";
    private static final String tableOfTablesName = "table_of_tables";
    private static final String tableOfRelationshipsName = "table_of_relationships";
    private static final String tableOfColumnsName = "table_of_columns";
    private static final String tableOfColumnValueOptionsName = "table_of_column_value_options";
    private static final String tableOfColumnInputMarkupElementsName = "table_of_column_input_markup_elements";
    private static final List<String> specialTableNameList;

    static {
        specialTableNameList = new ArrayList<>();
        specialTableNameList.add(tableOfSchemasName);
        specialTableNameList.add(tableOfTablesName);
        specialTableNameList.add(tableOfRelationshipsName);
        specialTableNameList.add(tableOfColumnsName);
        specialTableNameList.add(tableOfColumnValueOptionsName);
        specialTableNameList.add(tableOfColumnInputMarkupElementsName);
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

    public static String getTableOfColumnValueOptionsName(){
        return tableOfColumnValueOptionsName;
    }

    public static String getTableOfColumnInputMarkupElementsName(){
        return tableOfColumnInputMarkupElementsName;
    }

    public static List<String> getSpecialTableNameList(){
        return specialTableNameList;
    }
}
