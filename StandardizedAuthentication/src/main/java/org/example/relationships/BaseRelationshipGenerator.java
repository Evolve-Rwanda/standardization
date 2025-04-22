package org.example.relationships;

import org.example.tables.Table;
import org.example.tables.TableNameGiver;

import java.util.List;
import java.util.Map;



public class BaseRelationshipGenerator {


    private final Map<String, Table> nameTableMap;

    public BaseRelationshipGenerator(Map<String, Table> nameTableMap){
        this.nameTableMap = nameTableMap;
    }

    public List<Relationship> getRelationshipList(){
        // Base table relationships necessary for standardized authentication
        Table userTable = nameTableMap.get(TableNameGiver.getUserTableName());
        Table roleTable = nameTableMap.get(TableNameGiver.getRoleTableName());
        Table involvedEntityTable = nameTableMap.get(TableNameGiver.getInvolvedEntityTableName());
        Table addressTable = nameTableMap.get(TableNameGiver.getAddressTableTableName());
        Table contactTable = nameTableMap.get(TableNameGiver.getContactTableName());
        Table contactTypeTable = nameTableMap.get(TableNameGiver.getContactTypeTableName());
        Table authHistTable = nameTableMap.get(TableNameGiver.getAuthenticationHistoryTableName());
        Table authMetadataTable = nameTableMap.get(TableNameGiver.getGetAuthenticationMetadataTableName());
        Table privilegeTable = nameTableMap.get(TableNameGiver.getPrivilegeTableName());
        new Relationship(userTable, roleTable, "*:*");
        new Relationship(userTable, involvedEntityTable, "*:*");
        new Relationship(userTable, addressTable, "*:*");
        new Relationship(userTable, contactTable, "*:*");
        new Relationship(contactTypeTable, contactTable, "1:*");
        new Relationship(userTable, authHistTable, "1:*");
        new Relationship(authHistTable, authMetadataTable, "1:1");
        new Relationship(roleTable, privilegeTable, "1:*");
        new Relationship(involvedEntityTable, contactTable, "*:*");
        new Relationship(involvedEntityTable, addressTable, "*:*");
        return Relationship.getRelationshipList();
    }
}
