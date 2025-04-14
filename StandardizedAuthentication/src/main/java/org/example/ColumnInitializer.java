package org.example;

import java.util.List;
import java.util.ArrayList;


public class ColumnInitializer {


    static ColumnInitializer instance = null;

    private ColumnInitializer(){
    }

    public static ColumnInitializer getColumnInitializer(){
        if(instance == null)
            return new ColumnInitializer();
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
        String tableName = "table_of_schemas";
        String timestamp = DateTime.getTimeStamp();
        tosColumnList.add(new Column(tableName, "id", 1, "SERIAL", 7, 0, null, false, false, false, true, false, false, null, null, null, "", "", timestamp, null));
        tosColumnList.add(new Column(tableName, "schema_name", 2,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tosColumnList.add(new Column(tableName, "description", 3,"TEXT", 0, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        tosColumnList.add(new Column(tableName, "created_at", 4,"TIMESTAMP", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tosColumnList.add(new Column(tableName, "deleted_at", 5,"TIMESTAMP", 7, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        return tosColumnList;
    }

    public List<Column> getTableOfTablesColumnList(){
        List<Column> totColumnList = new ArrayList<>();
        String tableName = "table_of_tables";
        String timestamp = DateTime.getTimeStamp();
        totColumnList.add(new Column(tableName, "id", 1, "SERIAL", 7, 0, null, false, false, false, true, false, false, null, null, null, "", "", timestamp, null));
        totColumnList.add(new Column(tableName, "table_name", 2,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        totColumnList.add(new Column(tableName, "schema_name", 3,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        totColumnList.add(new Column(tableName, "description", 4,"TEXT", 0, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        totColumnList.add(new Column(tableName, "created_at", 5,"TIMESTAMP", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        totColumnList.add(new Column(tableName, "deleted_at", 6,"TIMESTAMP", 7, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        return totColumnList;
    }


    public List<Column> getTableOfRelationshipsColumnList(){
        List<Column> torColumnList = new ArrayList<>();
        String tableName = "table_of_relationships";
        String timestamp = DateTime.getTimeStamp();
        torColumnList.add(new Column(tableName, "id", 1, "SERIAL", 7, 0, null, false, false, false, true, false, false, null, null, null, "", "", timestamp, null));
        torColumnList.add(new Column(tableName, "left_table_name", 2,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        torColumnList.add(new Column(tableName, "right_table_name", 3,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        torColumnList.add(new Column(tableName, "type", 4,"VARCHAR", 10, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        torColumnList.add(new Column(tableName, "description", 5,"TEXT", 0, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        torColumnList.add(new Column(tableName, "created_at", 6,"TIMESTAMP", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        torColumnList.add(new Column(tableName, "deleted_at", 7,"TIMESTAMP", 7, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        return torColumnList;
    }

    public List<Column> getTableOfColumnsColumnList(){
        List<Column> tocColumnList = new ArrayList<>();
        String tableName = "table_of_columns";
        String timestamp = DateTime.getTimeStamp();
        tocColumnList.add(new Column(tableName, "column_id", 1, "SERIAL", 7, 0, null, false, false, false, true, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "table_name", 2,"VARCHAR", 100, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
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

        tocColumnList.add(new Column(tableName, "created_at", 20,"TIMESTAMP", 7, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        tocColumnList.add(new Column(tableName, "deleted_at", 21,"TIMESTAMP", 7, 0, null, true, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return tocColumnList;
    }

    public List<Column> getUserColumnList(){

        List<Column> userColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = "user";

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
        String tableName = "involved_entity";

        entityColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        entityColumnList.add(new Column(tableName, "name", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        entityColumnList.add(new Column(tableName, "status", 3,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return entityColumnList;
    }

    public List<Column> getRoleColumnList(){

        List<Column> roleColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = "role";

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
        String tableName = "privilege";

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
        String tableName = "contact";

        contactColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        contactColumnList.add(new Column(tableName, "value", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        contactColumnList.add(new Column(tableName, "status", 3,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return contactColumnList;
    }

    public List<Column> getContactTypeColumnList(){

        List<Column> contactColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = "contact_type";

        contactColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        contactColumnList.add(new Column(tableName, "name", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        contactColumnList.add(new Column(tableName, "status", 3,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return contactColumnList;
    }

    public List<Column> getAddressColumnList(){

        List<Column> addressColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = "address";

        addressColumnList.add(new Column(tableName, "id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        addressColumnList.add(new Column(tableName, "country", 2,"VARCHAR", 255, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        addressColumnList.add(new Column(tableName, "status", 3,"VARCHAR", 20, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return addressColumnList;
    }

    public List<Column> getAuthHistoryColumnList(){

        List<Column> authHistColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = "authentication_history";

        authHistColumnList.add(new Column(tableName, "session_id", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "started_at", 2,"TIMESTAMP", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "ended_at", 3,"TIMESTAMP", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "deleted_at", 4,"TIMESTAMP", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));

        return authHistColumnList;
    }

    public List<Column> getAuthMetadataColumnList(){

        List<Column> authHistColumnList = new ArrayList<>();
        String timestamp = DateTime.getTimeStamp();
        String tableName = "authentication_metadata";

        authHistColumnList.add(new Column(tableName, "ip_address", 1, "VARCHAR", 50, 0, null, false, false, false, true, false, true, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "mac_address", 2,"VARCHAR", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "browser_type", 3,"VARCHAR", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "os", 4,"VARCHAR", 25, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        authHistColumnList.add(new Column(tableName, "referrer", 5,"TEXT", 0, 0, null, false, false, false, false, false, false, null, null, null, "", "", timestamp, null));
        
        return authHistColumnList;
    }

}
