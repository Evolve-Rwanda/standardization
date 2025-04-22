package org.example.schemas;

import org.example.utilities.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SchemaGenerator {

    private Map<String, Schema> nameSchemaMap;
    private List<Schema> schemaList;

    public SchemaGenerator(){
        this.generateNameSchemaMap();
    }

    public void generateNameSchemaMap(){
        // Create the two most important schemas
        String docSchema = SchemaNameGiver.getDocumentationSchemaName();
        String umSchema = SchemaNameGiver.getUserManagementSchemaName();
        Schema userManagementSchema = new Schema(umSchema, "Concerned with user management tables", DateTime.getTimeStamp(), "");
        Schema databaseDocumentationSchema = new Schema(docSchema, "Concerned with documenting the database objects", DateTime.getTimeStamp(), "");
        this.schemaList = Schema.getSchemaList();
        // Keep the schemas in a hashmap
        Map<String, Schema> nameSchemaMap = new HashMap<>();
        nameSchemaMap.put(docSchema, databaseDocumentationSchema);
        nameSchemaMap.put(umSchema, userManagementSchema);
        this.nameSchemaMap = nameSchemaMap;
    }

    public Map<String, Schema> getNameSchemaMap(){
        return this.nameSchemaMap;
    }

    public List<Schema> getSchemaList(){
        return this.schemaList;
    }
}
