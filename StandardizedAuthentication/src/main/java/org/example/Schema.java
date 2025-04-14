package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Schema {

    private final String name;
    private final String description;
    private final String createdAt;
    private final String deletedAt;
    private static final List<Schema> schemaList;

    static {
        schemaList = new ArrayList<>();
    }

    public Schema(String name, String description, String createdAt, String deletedAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        schemaList.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Schema schema)) return false;
        return Objects.equals(name, schema.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Schema - "  + name + ": " + description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public static List<Schema> getSchemaList(){
        return schemaList;
    }
}
