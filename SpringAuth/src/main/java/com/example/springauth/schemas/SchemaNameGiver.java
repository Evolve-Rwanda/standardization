package com.example.springauth.schemas;


public class SchemaNameGiver {

    private static final String documentation = "DB_DOC";
    private static final String userManagement = "UM";

    public static String getDocumentationSchemaName(){
        return documentation;
    }

    public static String getUserManagementSchemaName(){
        return userManagement;
    }
}
