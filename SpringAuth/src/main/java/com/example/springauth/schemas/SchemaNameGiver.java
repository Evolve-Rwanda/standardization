package com.example.springauth.schemas;


public class SchemaNameGiver {

    public static final String documentation = "DB_DOC";
    public static final String userManagement = "UM";

    public static String getDocumentationSchemaName(){
        return documentation;
    }

    public static String getUserManagementSchemaName(){
        return userManagement;
    }
}
