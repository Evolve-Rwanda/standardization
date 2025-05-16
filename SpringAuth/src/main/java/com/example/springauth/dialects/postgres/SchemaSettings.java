package com.example.springauth.dialects.postgres;

import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaGenerator;

import java.util.Map;

public class SchemaSettings {

    private static SchemaSettings schemaSettings;

    private SchemaSettings() {

    }

    public static SchemaSettings getInstance() {
        if (schemaSettings == null) {
            schemaSettings = new SchemaSettings();
        }
        return schemaSettings;
    }

    private SchemaGenerator getSchemaGenerator() {
        return new SchemaGenerator();
    }

    public Schema getSchema(String schemaName) {
        Map<String, Schema> nameSchemaMap = this.getSchemaGenerator().getNameSchemaMap();
        return nameSchemaMap.get(schemaName);
    }
}
