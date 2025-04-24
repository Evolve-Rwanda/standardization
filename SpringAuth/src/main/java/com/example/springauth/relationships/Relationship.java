package com.example.springauth.relationships;

import com.example.springauth.tables.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Relationship {


    private final Table leftTable;
    private final Table rightTable;
    private final String type;
    private String description;
    private static final List<Relationship> relationshipList;

    static {
        relationshipList = new ArrayList<>();
    }

    public Relationship(Table leftTable, Table rightTable, String type){
        this.leftTable = leftTable;
        this.rightTable = rightTable;
        this.type = type;
        relationshipList.add(this);
    }

    public Relationship(Table leftTable, Table rightTable, String type, String description){
        this.leftTable = leftTable;
        this.rightTable = rightTable;
        this.type = type;
        this.description = description;
        relationshipList.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Relationship that)) return false;
        return Objects.equals(leftTable, that.leftTable) && Objects.equals(rightTable, that.rightTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftTable, rightTable);
    }

    @Override
    public String toString(){
        return leftTable + " <-> " + rightTable + " - [" + type + "]";
    }

    public Table getLeftTable() {
        return leftTable;
    }

    public Table getRightTable() {
        return rightTable;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<Relationship> getRelationshipList() {
        return relationshipList;
    }
    
}
