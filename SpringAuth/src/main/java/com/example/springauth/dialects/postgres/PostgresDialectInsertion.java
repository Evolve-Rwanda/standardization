package com.example.springauth.dialects.postgres;

import com.example.springauth.models.app.*;
import com.example.springauth.schemas.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PostgresDialectInsertion {



    private final Schema tableSchema;
    private final QueryExecutor queryExecutor;
    private final String tableName;

    private Map<String, String> fieldNameTypeNameMap;
    private Map<String, Boolean> typeNameNumericFlagMap;

    {
        fieldNameTypeNameMap = new HashMap<>();
        typeNameNumericFlagMap = new HashMap<>();
    }

    public PostgresDialectInsertion(QueryExecutor queryExecutor, Schema tableSchema, String tableName){
        this.queryExecutor = queryExecutor;
        this.tableSchema = tableSchema;
        this.tableName = tableName;
    }

    public Map<String, String> getFieldNameTypeNameMap() {
        return fieldNameTypeNameMap;
    }

    public void setFieldNameTypeNameMap(Map<String, String> fieldNameTypeNameMap) {
        this.fieldNameTypeNameMap = fieldNameTypeNameMap;
    }

    public Map<String, Boolean> getTypeNameNumericFlagMap() {
        return typeNameNumericFlagMap;
    }

    public void setTypeNameNumericFlagMap(Map<String, Boolean> typeNameNumericFlagMap) {
        this.typeNameNumericFlagMap = typeNameNumericFlagMap;
    }

    private boolean isFieldNumeric(String fieldName) {
        if (fieldNameTypeNameMap.containsKey(fieldName)) {
            String typeName = fieldNameTypeNameMap.get(fieldName);
            if (typeNameNumericFlagMap.containsKey(typeName)) {
                return typeNameNumericFlagMap.get(typeName);
            }
        }
        return false;
    }

    public UserModel insertUser(UserModel userModel){
        String setPathQuery = this.getSchemaPathQuery();
        String insertQuery = this.getInsertUserQuery(userModel);
        System.out.println(setPathQuery + insertQuery);
        this.queryExecutor.executeQuery(setPathQuery + insertQuery);
        this.queryExecutor.closeResources();
        return userModel;
    }

    public RoleModel insertRole(RoleModel roleModel){
        String setPathQuery = this.getSchemaPathQuery();
        String insertQuery = this.getRoleInsertionQuery(roleModel);
        this.queryExecutor.executeQuery(setPathQuery + insertQuery);
        this.queryExecutor.closeResources();
        return roleModel;
    }

    public PrivilegeModel insertPrivilege(PrivilegeModel privilegeModel){
        String setPathQuery = this.getSchemaPathQuery();
        String insertQuery = this.getPrivilegeInsertionQuery(privilegeModel);
        this.queryExecutor.executeQuery(setPathQuery + insertQuery);
        this.queryExecutor.closeResources();
        return privilegeModel;
    }

    public List<RolePrivilegeMapModel> insertRolePrivilegeMappings(
            List<RolePrivilegeMapModel> rolePrivilegeMapModels
    ){
        List<RolePrivilegeMapModel> rolePrivilegeMapModelList = new ArrayList<>();
        for(RolePrivilegeMapModel rolePrivilegeMapModel : rolePrivilegeMapModels){
            RolePrivilegeMapModel returnedRolePrivilegeMapModel = this.insertRolePrivilegeMap(rolePrivilegeMapModel);
            rolePrivilegeMapModelList.add(returnedRolePrivilegeMapModel);
        }
        return rolePrivilegeMapModelList;
    }

    public RolePrivilegeMapModel insertRolePrivilegeMap(RolePrivilegeMapModel rolePrivilegeMapModel){
        String setPathQuery = this.getSchemaPathQuery();
        String insertQuery = this.getRolePrivilegeMapInsertionQuery(rolePrivilegeMapModel);
        this.queryExecutor.executeQuery(setPathQuery + insertQuery);
        this.queryExecutor.closeResources();
        return rolePrivilegeMapModel;
    }

    public UserRoleMapModel insertUserRoleMap(UserRoleMapModel userRoleMapModel){
        String setPathQuery = this.getSchemaPathQuery();
        String insertQuery = this.getUserRoleMapInsertionQuery(userRoleMapModel);
        this.queryExecutor.executeQuery(setPathQuery + insertQuery);
        this.queryExecutor.closeResources();
        return userRoleMapModel;
    }

    private String getInsertUserQuery(UserModel userModel){

        StringBuilder targetColumnBuilder = new StringBuilder();
        StringBuilder columnValueBuilder = new StringBuilder();
        List<EntityPropModel> entityPropModelList = userModel.getUserPropModelList();
        int i=1;
        int size = entityPropModelList.size();

        for(EntityPropModel entityPropModel: entityPropModelList){
            boolean isNumeric = this.isFieldNumeric(entityPropModel.getName());
            if(i < size){
                targetColumnBuilder
                        .append(entityPropModel.getName())
                        .append(",");
                // if it is non-numeric in type. Source code required to be able to tell.
                // there is need to generate a column type map for all dynamic entities in the system.
                if(!isNumeric) {
                    columnValueBuilder
                            .append("'")
                            .append(entityPropModel.getValue())
                            .append("'")
                            .append(",");
                }else {
                    columnValueBuilder
                            .append(entityPropModel.getValue())
                            .append(",");
                }
            }else{
                targetColumnBuilder
                        .append(entityPropModel.getName());
                if(!isNumeric) {
                    columnValueBuilder
                            .append("'")
                            .append(entityPropModel.getValue())
                            .append("'");
                }else {
                    columnValueBuilder
                            .append(entityPropModel.getValue());
                }
            }
            i++;
        }
        return String.format(
                "INSERT INTO \"%s\"(%s) VALUES(%s);",
                this.tableName, targetColumnBuilder.toString(), columnValueBuilder.toString()
        );
    }

    public String getRoleInsertionQuery(RoleModel roleModel){
        String id = roleModel.getId();
        String code = roleModel.getCode();
        String name = roleModel.getName();
        String description = roleModel.getDescription();
        String createdBy = roleModel.getCreatedBy();
        String status = roleModel.getStatus();
        String createdAt = roleModel.getCreatedAt();
        String targetColumns = "id, code, name, description, created_by, status, created_at";
        String columnValues = String.format(
                "'%s', '%s', '%s', '%s', '%s', '%s', '%s'",
                id, code, name, description, createdBy, status, createdAt
        );
        return String.format(
                "INSERT INTO \"%s\"(%s) VALUES(%s);",
                this.tableName, targetColumns, columnValues
        );
    }

    public String getPrivilegeInsertionQuery(PrivilegeModel privilegeModel){
        String id = privilegeModel.getId();
        String code = privilegeModel.getCode();
        String name = privilegeModel.getName();
        String description = privilegeModel.getDescription();
        String approvalMethod = privilegeModel.getApprovalMethod();
        String createdBy = privilegeModel.getCreatedBy();
        String status = privilegeModel.getStatus();
        String createdAt = privilegeModel.getCreatedAt();
        String targetColumns = "id, code, name, description, approval_method, created_by, status, created_at";
        String columnValues = String.format(
                "'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'",
                id, code, name, description, approvalMethod, createdBy, status, createdAt
        );
        return String.format(
                "INSERT INTO \"%s\"(%s) VALUES(%s);",
                this.tableName, targetColumns, columnValues
        );
    }

    public String getRolePrivilegeMapInsertionQuery(RolePrivilegeMapModel rolePrivilegeMapModel){
        String roleId = rolePrivilegeMapModel.getRole().getId();
        String privilegeId = rolePrivilegeMapModel.getPrivilege().getId();
        String createdAt = rolePrivilegeMapModel.getCreatedAt();
        String targetColumns = "role_id, privilege_id, created_at";
        String columnValues = String.format("'%s', '%s', '%s'", roleId, privilegeId, createdAt);
        return String.format(
                "INSERT INTO \"%s\"(%s) VALUES(%s);",
                this.tableName, targetColumns, columnValues
        );
    }

    public String getUserRoleMapInsertionQuery(UserRoleMapModel userRoleMapModel){
        String userId = userRoleMapModel.getUserId();
        String roleId = userRoleMapModel.getRoleId();
        String createdAt = userRoleMapModel.getCreatedAt();
        String targetColumns = "user_id, role_id, created_at";
        String columnValues = String.format("'%s', '%s', '%s'", userId, roleId, createdAt);
        return String.format(
                "INSERT INTO \"%s\"(%s) VALUES(%s);",
                this.tableName, targetColumns, columnValues
        );
    }

    private String getSchemaPathQuery(){
        return String.format("SET search_path TO %s;", this.tableSchema.getName());
    }

}
