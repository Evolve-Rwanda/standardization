package com.example.springauth.dialects.postgres;

import com.example.springauth.models.app.PrivilegeModel;
import com.example.springauth.models.app.RoleModel;
import com.example.springauth.schemas.Schema;



public class PostgresDialectInsertion {


    private final Schema tableSchema;
    private final QueryExecutor queryExecutor;
    private final String tableName;


    public PostgresDialectInsertion(QueryExecutor queryExecutor, Schema tableSchema, String tableName){
        this.queryExecutor = queryExecutor;
        this.tableSchema = tableSchema;
        this.tableName = tableName;
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
        System.out.println(setPathQuery + insertQuery);
        this.queryExecutor.executeQuery(setPathQuery + insertQuery);
        this.queryExecutor.closeResources();
        return privilegeModel;
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

    private String getSchemaPathQuery(){
        return String.format("SET search_path TO %s;", this.tableSchema.getName());
    }

}
