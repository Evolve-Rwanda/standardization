package com.example.springauth.relationships;

import com.example.springauth.columns.Column;
import com.example.springauth.schemas.Schema;
import com.example.springauth.tables.DerivedTable;
import com.example.springauth.tables.Table;

import java.util.ArrayList;
import java.util.List;



public class RelationshipResolver {


    public RelationshipResolver() {
    }

    // Resolving many-to-many relationships
    public List<Table> resolveManyToManyRelationships(List<Relationship> relationshipList){
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
            Schema derivedTableSchema = leftTableSchema.equals(rightTableSchema) ? rightTableSchema : null;

            if(type.equalsIgnoreCase("*:*")){
                List<Column> derivedTableColumnList = new ArrayList<>();
                List<Column> columnList = new ArrayList<>();
                columnList.addAll(leftTablePrimaryKeyList);
                columnList.addAll(rightTablePrimaryKeyList);
                String derivedTableName = (leftTableName + "_" + rightTableName + "_map").replace("\"", "");
                String derivedTableNameFQN = derivedTableSchema != null && !derivedTableSchema.getName().isEmpty() ? (derivedTableSchema.getName() + "." + derivedTableName) : derivedTableName;
                int n = 1;
                for (Column column: columnList){
                    try {
                        String tableName = leftTable.getUniversalColumnList().contains(column) ? leftTable.getName() : rightTable.getName();
                        tableName = tableName.replace("\"", "");
                        String cloneName = (tableName + "_" + column.getName()).replace("\"", "");
                        Column clone = column.clone();
                        clone.setTableName(derivedTableNameFQN);
                        clone.setName(cloneName);
                        clone.setIsPK("true");
                        clone.setIsFK("true");
                        clone.setReferenceColumnName(column.getName());
                        clone.setReferenceTableName(tableName);
                        clone.setNumber(n);
                        derivedTableColumnList.add(clone);
                        n++;
                    }catch (CloneNotSupportedException e){
                        System.out.println(e.getMessage());
                    }
                }
                Table derivedTable = new DerivedTable(derivedTableName, derivedTableColumnList);
                if (leftTable.getSchema().equals(rightTable.getSchema()))
                    derivedTable.setSchema(leftTable.getSchema());
                derivedTableList.add(derivedTable);
            }
        }
        return derivedTableList;
    }

    public List<Table> ensureEntityAndReferentialIntegrity(List<Relationship> relationshipList){
        List<Table> enhancedTableList = new ArrayList<>();
        for(Relationship relationship: relationshipList){
            Table strongTable = relationship.getLeftTable();
            List<Column> strongTablePrimaryKeyList = strongTable.getPrimaryKeyList();
            Table weakTable = relationship.getRightTable();
            String type = relationship.getType().trim();

            Schema strongTableSchema = strongTable.getSchema() != null ? strongTable.getSchema() : null;
            String strongTableFQN = strongTableSchema != null ? strongTableSchema.getName() + "." + strongTable.getName() : strongTable.getName();

            Schema weakTableSchema = weakTable.getSchema() != null ? weakTable.getSchema() : null;
            String weakTableFQN = weakTableSchema != null ? weakTableSchema.getName() + "." + weakTable.getName() : weakTable.getName();
            if (type.equalsIgnoreCase("1:*")
                    || type.equalsIgnoreCase("1:1")
            ) {
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
            }
            if (!enhancedTableList.contains(weakTable))
                enhancedTableList.add(weakTable);
            if (!enhancedTableList.contains(strongTable))
                enhancedTableList.add(strongTable);
        }
        return enhancedTableList;
    }

}
