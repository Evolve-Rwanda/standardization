package com.example.springauth.tables;


public class TableNameGiver {



    public static final String userTableName = "user";
    private static final String addressTableTableName = "address";
    private static final String involvedEntityTableName = "involved_entity";
    private static final String roleTableName = "role";
    private static final String privilegeTableName = "privilege";
    private static final String contactTableName = "contact";
    private static final String contactTypeTableName = "contact_type";
    private static final String authenticationHistoryTableName = "authentication_history";
    private static final String authenticationMetadataTableName = "authentication_metadata";
    // Derived tables
    private static final String rolePrivilegeMapTableName = "role_privilege_map";
    private static final String userRoleMapTableName = "user_role_map";


    public static String getUserTableName(){
        return userTableName;
    }

    public static String getAddressTableTableName(){
        return addressTableTableName;
    }

    public static String getInvolvedEntityTableName(){
        return involvedEntityTableName;
    }

    public static String getRoleTableName(){
        return roleTableName;
    }

    public static String getPrivilegeTableName(){
        return privilegeTableName;
    }

    public static String getRolePrivilegeMapTableName(){
        return rolePrivilegeMapTableName;
    }

    public static String getContactTableName(){
        return contactTableName;
    }

    public static String getContactTypeTableName(){
        return contactTypeTableName;
    }

    public static String getAuthenticationHistoryTableName(){
        return authenticationHistoryTableName;
    }

    public static String getGetAuthenticationMetadataTableName(){
        return authenticationMetadataTableName;
    }

    public static String getUserRoleMapTableName(){
        return userRoleMapTableName;
    }

}
