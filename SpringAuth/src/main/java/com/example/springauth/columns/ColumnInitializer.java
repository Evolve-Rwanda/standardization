package com.example.springauth.columns;

import com.example.springauth.specialtables.SpecialTableNameGiver;
import com.example.springauth.tables.TableNameGiver;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.schemas.Schema;

import java.util.ArrayList;
import java.util.List;


public class ColumnInitializer {


    static ColumnInitializer instance = null;
    private Schema databaseDocumentationSchema;
    private String dbDocSchemaName;
    private Schema userManagementSchema;
    private String umSchemaName;

    private ColumnInitializer(Schema databaseDocumentationSchema, Schema userManagementSchema){
        this.databaseDocumentationSchema = databaseDocumentationSchema;
        this.dbDocSchemaName = databaseDocumentationSchema != null ? databaseDocumentationSchema.getName() : "";
        this.userManagementSchema = userManagementSchema;
        this.umSchemaName = userManagementSchema != null ? userManagementSchema.getName() : "";
    }

    public static ColumnInitializer getColumnInitializer(Schema databaseDocumentationSchema, Schema userManagementSchema){
        if(instance == null)
            return new ColumnInitializer(databaseDocumentationSchema, userManagementSchema);
        return instance;
    }

    public List<Column> getCommonColumnList(){
        List<Column> commonColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        commonColumnList.add(new Column("any", "created_at", 1,"TIMESTAMP", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        commonColumnList.add(new Column("any", "last_updated_at", 2,"TIMESTAMP", 255, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        commonColumnList.add(new Column("any", "deleted_at", 3,"TIMESTAMP", 255, 0, null, true, false, true, false, false, false, null, null, null, "", "", timestamp, null));
        return commonColumnList;
    }

    public List<Column> getTableOfSchemasColumnList(){
        List<Column> tosColumnList = new ArrayList<>();
        String tableName = SpecialTableNameGiver.getTableOfSchemasName();
        tableName = !dbDocSchemaName.isEmpty() ? (dbDocSchemaName + "." + tableName): tableName;
        String timestamp = DateTime.getTimeStamp();
        tosColumnList.add(new Column(tableName, "id", 1, "SERIAL", 7, 0, null, false, false, false, true, false, false, null, null, null, "", "", timestamp, null));
        tosColumnList.add(new Column(tableName, "schema_name", 2,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tosColumnList.add(new Column(tableName, "description", 3,"TEXT", 0, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return tosColumnList;
    }

    public List<Column> getTableOfTablesColumnList(){
        List<Column> totColumnList = new ArrayList<>();
        String tableName = SpecialTableNameGiver.getTableOfTablesName();
        tableName = !dbDocSchemaName.isEmpty() ? (dbDocSchemaName + "." + tableName): tableName;
        String timestamp = DateTime.getTimeStamp();
        totColumnList.add(new Column(tableName, "id", 1, "SERIAL", 7, 0, null, false, false, false, true, false, false, null, null, null, "", "", timestamp, null));
        totColumnList.add(new Column(tableName, "table_name", 2,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        totColumnList.add(new Column(tableName, "schema_name", 3,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        totColumnList.add(new Column(tableName, "description", 4,"TEXT", 0, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        return totColumnList;
    }


    public List<Column> getTableOfRelationshipsColumnList(){
        List<Column> torColumnList = new ArrayList<>();
        String tableName = SpecialTableNameGiver.getTableOfRelationshipsName();
        tableName = !dbDocSchemaName.isEmpty() ? (dbDocSchemaName + "." + tableName): tableName;
        String timestamp = DateTime.getTimeStamp();
        torColumnList.add(new Column(tableName, "id", 1, "SERIAL", 7, 0, null, false, false, false, true, false, false, null, null, null, "", "", timestamp, null));
        torColumnList.add(new Column(tableName, "left_table_name", 2,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "The fully qualified name (FQN) of the left table in this relationship", timestamp, null));
        torColumnList.add(new Column(tableName, "right_table_name", 3,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "The fully qualified name (FQN) of the right table in this relationship", timestamp, null));
        torColumnList.add(new Column(tableName, "type", 4,"VARCHAR", 10, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        torColumnList.add(new Column(tableName, "description", 5,"TEXT", 0, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return torColumnList;
    }

    public List<Column> getTableOfColumnsColumnList(){
        List<Column> tocColumnList = new ArrayList<>();
        String tableName = SpecialTableNameGiver.getTableOfColumnsName();
        tableName = !dbDocSchemaName.isEmpty() ? (dbDocSchemaName + "." + tableName): tableName;
        String timestamp = DateTime.getTimeStamp();
        tocColumnList.add(new Column(tableName, "column_id", 1, "SERIAL", 7, 0, null, false, false, false, true, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "table_name", 2,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "The fully qualified name of the table in which the column can be found", timestamp, null));
        tocColumnList.add(new Column(tableName, "column_number", 3,"SMALLINT", 3, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "column_name", 4,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "column_data_type", 5,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "precision", 6,"SMALLINT", 3, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "scale", 7,"SMALLINT", 3, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "default_value", 8,"TEXT", 0, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "is_nullable", 9,"VARCHAR", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "is_pk", 10,"VARCHAR", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "is_fk", 11,"VARCHAR", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        tocColumnList.add(new Column(tableName, "reference_table_name", 12,"VARCHAR", 100, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "reference_column_name", 13,"VARCHAR", 100, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "on_update_action", 14,"VARCHAR", 100, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "on_delete_action", 15,"VARCHAR", 7, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        tocColumnList.add(new Column(tableName, "is_a_fact_based_column", 16,"VARCHAR", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "is_encrypted", 17,"VARCHAR", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "is_indexed", 18,"VARCHAR", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "description", 19,"TEXT", 0, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return tocColumnList;
    }

    public List<Column> getUserColumnList(){

        List<Column> userColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = TableNameGiver.getUserTableName();
        tableName = !umSchemaName.isEmpty() ? (umSchemaName + "." + tableName): tableName;

        userColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "first_name", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "last_name", 3,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "other_names", 4,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "gender", 5,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "date_of_birth", 6,"DATE", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "id_type", 7,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "",  timestamp, null));
        userColumnList.add(new Column(tableName, "id_number", 8,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "current_phone_number", 9,"VARCHAR", 30, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "current_photo", 10,"VARCHAR", 255, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "current_password", 11,"VARCHAR", 255, 0, null, false, false, true, false, false, false, null, null, null, "", "", timestamp, null));
        userColumnList.add(new Column(tableName, "authentication_hash", 12,"VARCHAR", 255, 0, null, false, false, true, false, false, true, null, null, null, "", "", timestamp, null));

        return userColumnList;
    }

    public List<Column> getInvolvedEntityColumnList(){

        List<Column> entityColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = TableNameGiver.getInvolvedEntityTableName();
        tableName = !umSchemaName.isEmpty() ? (umSchemaName + "." + tableName): tableName;

        entityColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        entityColumnList.add(new Column(tableName, "name", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        entityColumnList.add(new Column(tableName, "status", 3,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return entityColumnList;
    }

    public List<Column> getRoleColumnList(){

        List<Column> roleColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = TableNameGiver.getRoleTableName();
        tableName = !umSchemaName.isEmpty() ? (umSchemaName + "." + tableName): tableName;

        roleColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        roleColumnList.add(new Column(tableName, "code", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        roleColumnList.add(new Column(tableName, "name", 3,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        roleColumnList.add(new Column(tableName, "description", 4,"TEXT", 0, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        roleColumnList.add(new Column(tableName, "created_by", 5,"VARCHAR", 50, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        roleColumnList.add(new Column(tableName, "status", 6,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return roleColumnList;
    }

    public List<Column> getPrivilegeColumnList(){

        List<Column> privilegeColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = TableNameGiver.getPrivilegeTableName();
        tableName = !umSchemaName.isEmpty() ? (umSchemaName + "." + tableName): tableName;

        privilegeColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        privilegeColumnList.add(new Column(tableName, "code", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        privilegeColumnList.add(new Column(tableName, "name", 3,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        privilegeColumnList.add(new Column(tableName, "description", 4,"TEXT", 0, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        privilegeColumnList.add(new Column(tableName, "approval_method", 5,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        privilegeColumnList.add(new Column(tableName, "created_by", 6,"VARCHAR", 50, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        privilegeColumnList.add(new Column(tableName, "status", 7,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return privilegeColumnList;
    }

    public List<Column> getContactColumnList(){

        List<Column> contactColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = TableNameGiver.getContactTableName();
        tableName = !umSchemaName.isEmpty() ? (umSchemaName + "." + tableName): tableName;

        contactColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        contactColumnList.add(new Column(tableName, "value", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        contactColumnList.add(new Column(tableName, "status", 3,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return contactColumnList;
    }

    public List<Column> getContactTypeColumnList(){

        List<Column> contactColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = TableNameGiver.getContactTypeTableName();
        tableName = !umSchemaName.isEmpty() ? (umSchemaName + "." + tableName): tableName;

        contactColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        contactColumnList.add(new Column(tableName, "name", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        contactColumnList.add(new Column(tableName, "status", 3,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return contactColumnList;
    }

    public List<Column> getAddressColumnList(){

        List<Column> addressColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = TableNameGiver.getAddressTableTableName();
        tableName = !umSchemaName.isEmpty() ? (umSchemaName + "." + tableName): tableName;

        addressColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        addressColumnList.add(new Column(tableName, "country", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        addressColumnList.add(new Column(tableName, "status", 3,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return addressColumnList;
    }

    public List<Column> getAuthHistoryColumnList(){

        List<Column> authHistColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = TableNameGiver.getAuthenticationHistoryTableName();
        tableName = !umSchemaName.isEmpty() ? (umSchemaName + "." + tableName): tableName;

        authHistColumnList.add(new Column(tableName, "session_id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "started_at", 2,"TIMESTAMP", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "ended_at", 3,"TIMESTAMP", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "deleted_at", 4,"TIMESTAMP", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return authHistColumnList;
    }

    public List<Column> getAuthMetadataColumnList(){

        List<Column> authHistColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = TableNameGiver.getGetAuthenticationMetadataTableName();
        tableName = !umSchemaName.isEmpty() ? (umSchemaName + "." + tableName): tableName;

        authHistColumnList.add(new Column(tableName, "ip_address", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "mac_address", 2,"VARCHAR", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "browser_type", 3,"VARCHAR", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "os", 4,"VARCHAR", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "referrer", 5,"TEXT", 0, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return authHistColumnList;
    }

}
