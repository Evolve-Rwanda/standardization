package com.example.springauth.tables;

import com.example.springauth.columns.ColumnInitializer;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.specialtables.SpecialTableNameGiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TableGenerator {


    private final ColumnInitializer colInit;
    private final Map<String, Schema> categorySchemaMap;

    public TableGenerator(ColumnInitializer colInit, Map<String, Schema> categorySchemaMap){
        this.colInit = colInit;
        this.categorySchemaMap = categorySchemaMap;
    }

    public Map<String, Table> getNameTableMap(){
        Map<String, Table> nameTableMap = new HashMap<>();

        String docSchema = SchemaNameGiver.getDocumentationSchemaName();
        String umSchema = SchemaNameGiver.getUserManagementSchemaName();

        String tosName = SpecialTableNameGiver.getTableOfSchemasName();
        var tableOfSchemas = new Table(tosName, colInit.getTableOfSchemasColumnList());
        tableOfSchemas.setSchema(categorySchemaMap.get(docSchema));
        nameTableMap.put(tableOfSchemas.getName(), tableOfSchemas);

        String tot = SpecialTableNameGiver.getTableOfTablesName();
        var tableOfTables = new Table(tot, colInit.getTableOfTablesColumnList());
        tableOfTables.setSchema(categorySchemaMap.get(docSchema));
        nameTableMap.put(tableOfTables.getName(), tableOfTables);

        String tor = SpecialTableNameGiver.getTableOfRelationshipsName();
        var tableOfRelationships = new Table(tor, colInit.getTableOfRelationshipsColumnList());
        tableOfRelationships.setSchema(categorySchemaMap.get(docSchema));
        nameTableMap.put(tableOfRelationships.getName(), tableOfRelationships);

        String toc = SpecialTableNameGiver.getTableOfColumnsName();
        var tableOfColumns = new Table(toc, colInit.getTableOfColumnsColumnList());
        tableOfColumns.setSchema(categorySchemaMap.get(docSchema));
        nameTableMap.put(tableOfColumns.getName(), tableOfColumns);

        String user = TableNameGiver.getUserTableName();
        var userTable = new UserTable(user, colInit.getUserColumnList());
        userTable.setSchema(categorySchemaMap.get(umSchema));
        nameTableMap.put(userTable.getName(), userTable);

        String involvedEntity = TableNameGiver.getInvolvedEntityTableName();
        var involvedEntityTable = new InvolvedEntityTable(involvedEntity, colInit.getInvolvedEntityColumnList());
        involvedEntityTable.setSchema(categorySchemaMap.get(umSchema));
        nameTableMap.put(involvedEntityTable.getName(), involvedEntityTable);

        String role = TableNameGiver.getRoleTableName();
        var roleTable = new RoleTable(role, colInit.getRoleColumnList());
        roleTable.setSchema(categorySchemaMap.get(umSchema));
        nameTableMap.put(roleTable.getName(), roleTable);

        String privilege = TableNameGiver.getPrivilegeTableName();
        var privilegeTable = new PrivilegeTable(privilege, colInit.getPrivilegeColumnList());
        privilegeTable.setSchema(categorySchemaMap.get(umSchema));
        nameTableMap.put(privilegeTable.getName(), privilegeTable);

        String contact = TableNameGiver.getContactTableName();
        var contactTable = new ContactTable(contact, colInit.getContactColumnList());
        contactTable.setSchema(categorySchemaMap.get(umSchema));
        nameTableMap.put(contactTable.getName(), contactTable);

        String contactType = TableNameGiver.getContactTypeTableName();
        var contactTypeTable = new ContactTypeTable(contactType, colInit.getContactTypeColumnList());
        contactTypeTable.setSchema(categorySchemaMap.get(umSchema));
        nameTableMap.put(contactTypeTable.getName(), contactTypeTable);

        String address = TableNameGiver.getAddressTableTableName();
        var addressTable = new AddressTable(address, colInit.getAddressColumnList());
        addressTable.setSchema(categorySchemaMap.get(umSchema));
        nameTableMap.put(addressTable.getName(), addressTable);

        String authHist = TableNameGiver.getAuthenticationHistoryTableName();
        var authHistTable = new AuthenticationHistoryTable(authHist, colInit.getAuthHistoryColumnList());
        authHistTable.setSchema(categorySchemaMap.get(umSchema));
        nameTableMap.put(authHistTable.getName(), authHistTable);

        String authMetaData = TableNameGiver.getGetAuthenticationMetadataTableName();
        var authMetadataTable = new AuthenticationMetadataTable(authMetaData, colInit.getAuthMetadataColumnList());
        authMetadataTable.setSchema(categorySchemaMap.get(umSchema));
        nameTableMap.put(authMetadataTable.getName(), authMetadataTable);

        return nameTableMap;
    }

    public static List<Table> getUniversalTableList(Map<String, Table> nameTableMap){
        List<Table> universalTableList = new ArrayList<>();
        for (String tableName: nameTableMap.keySet()){
            Table nonSpecialTable = nameTableMap.get(tableName);
            universalTableList.add(nonSpecialTable);
        }
        return universalTableList;
    }

}
