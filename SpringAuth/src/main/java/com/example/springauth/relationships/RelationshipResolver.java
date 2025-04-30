package com.example.springauth.relationships;

import com.example.springauth.columns.Column;
import com.example.springauth.schemas.Schema;
import com.example.springauth.tables.DerivedTable;
import com.example.springauth.tables.Table;

import java.util.ArrayList;
import java.util.List;



public class RelationshipResolver {


    private final List<Relationship> relationshipList;


    public RelationshipResolver(List<Relationship> relationshipList) {
        this.relationshipList = relationshipList;
    }

    // Resolving many-to-many relationships
    public List<Table> resolveManyToManyRelationships(){

        List<Table> derivedTableList = new ArrayList<>();

        for(Relationship relationship: relationshipList){

            Table leftTable = relationship.getLeftTable();
            List<Column> leftTablePrimaryKeyList = leftTable.getPrimaryKeyList();
            Table rightTable = relationship.getRightTable();
            List<Column> rightTablePrimaryKeyList = rightTable.getPrimaryKeyList();
            String type = relationship.getType().trim();
            String leftTableName = relationship.getLeftTable().getName();
            Schema leftTableSchema = relationship.getLeftTable().getSchema();
            String rightTableName = relationship.getRightTable().getName();
            Schema rightTableSchema = relationship.getRightTable().getSchema();
            Schema derivedTableSchema = leftTableSchema != null ? leftTableSchema : rightTableSchema;

            if(!type.equalsIgnoreCase("*:*"))
                continue;

            List<Column> derivedTableColumnList = new ArrayList<>();
            List<Column> columnList = new ArrayList<>();
            columnList.addAll(leftTablePrimaryKeyList);
            columnList.addAll(rightTablePrimaryKeyList);
            String derivedTableName = (leftTableName + "_" + rightTableName + "_map").replace("\"", "");
            String derivedTableNameFQN = derivedTableSchema != null && !derivedTableSchema.getName().isEmpty() ? (derivedTableSchema.getName() + "." + derivedTableName) : derivedTableName;
            int n = 1;

            for (Column column: columnList){
                try {
                    Table referenceTable = leftTable.getUniversalColumnList().contains(column) ? leftTable : rightTable;
                    String referenceTableName = referenceTable.getName();
                    String referenceTableFQN = referenceTable.getFullyQualifiedName();
                    //referenceTableName = referenceTableName.replace("\"", "");
                    String cloneName = (referenceTableName + "_" + column.getName());//.replace("\"", "");
                    Column clone = column.clone();
                    clone.setTableName(derivedTableNameFQN);
                    clone.setName(cloneName);
                    clone.setIsPK("true");
                    clone.setIsFK("true");
                    clone.setReferenceColumnName(column.getName());
                    clone.setReferenceTableName(referenceTableFQN);
                    clone.setNumber(n);
                    derivedTableColumnList.add(clone);
                    n++;
                }catch (CloneNotSupportedException e){
                    System.out.println(e.getMessage());
                }
            }
            Table derivedTable = new DerivedTable(derivedTableName, derivedTableColumnList);
            if (derivedTableSchema != null) {
                derivedTable.setSchema(derivedTableSchema);
            }
            derivedTableList.add(derivedTable);
        }
        return derivedTableList;
    }

    public List<Table> ensureEntityAndReferentialIntegrity(){

        List<Table> enhancedTableList = new ArrayList<>();

        for(Relationship relationship: relationshipList){

            Table strongTable = relationship.getLeftTable();
            List<Column> strongTablePrimaryKeyList = strongTable.getPrimaryKeyList();
            Table weakTable = relationship.getRightTable();
            String type = relationship.getType().trim();
            boolean isTargetRelationship = type.equalsIgnoreCase("1:*") || type.equalsIgnoreCase("1:1");
            if (!isTargetRelationship)
                continue;

            Schema strongTableSchema = strongTable.getSchema() != null ? strongTable.getSchema() : null;
            String strongTableFQN = strongTableSchema != null ? strongTableSchema.getName() + "." + strongTable.getName() : strongTable.getName();

            Schema weakTableSchema = weakTable.getSchema() != null ? weakTable.getSchema() : null;
            String weakTableFQN = weakTableSchema != null ? weakTableSchema.getName() + "." + weakTable.getName() : weakTable.getName();

            for(Column column: strongTablePrimaryKeyList){
                try {
                    Column clone  = column.clone();
                    String parentTableName = strongTable.getName();
                    clone.setTableName(weakTableFQN);
                    String newColumnName = (parentTableName + "_" + clone.getName()).replace("\"", "");
                    clone.setName(newColumnName);
                    clone.setReferenceColumnName(column.getName());
                    clone.setReferenceTableName(strongTableFQN);
                    clone.setIsPK("false");
                    clone.setIsFK("true");
                    weakTable.addColumn(clone);
                }catch (CloneNotSupportedException e){
                    System.out.println(e.getMessage());
                }
            }

            if (!enhancedTableList.contains(weakTable))
                enhancedTableList.add(weakTable);
            if (!enhancedTableList.contains(strongTable))
                enhancedTableList.add(strongTable);
        }
        return enhancedTableList;
    }

}
