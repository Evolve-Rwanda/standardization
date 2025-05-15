package com.example.springauth.dialects.postgres;

import com.example.springauth.models.app.PrivilegeModel;
import com.example.springauth.models.app.RoleModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.tables.TableNameGiver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PostgresDialectSelection {


    private final Schema tableSchema;
    private final QueryExecutor queryExecutor;


    public PostgresDialectSelection(QueryExecutor queryExecutor, Schema tableSchema){
        this.queryExecutor = queryExecutor;
        this.tableSchema = tableSchema;
    }

    public List<RoleModel> selectRoles() throws SQLException {

        List<RoleModel> roleModelList = new ArrayList<>();
        String roleTableName = TableNameGiver.getRoleTableName();
        String tableFQN = tableSchema != null ? String.format("%s.%s", tableSchema.getName(), roleTableName) : roleTableName;
        String query = String.format("SELECT * FROM %s;", tableFQN);
        System.out.println(query);
        ResultWrapper resultWrapper = queryExecutor.executeQuery(query);
        ResultSet resultSet = resultWrapper.getResultSet();

        while (resultSet.next()) {

            String id = resultSet.getString("id");
            String code = resultSet.getString("code");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String status = resultSet.getString("status");
            String createdBy = resultSet.getString("created_by");
            String createdAt = resultSet.getString("created_at");
            String lastUpdatedAt = resultSet.getString("last_updated_at");
            String deletedAt = resultSet.getString("deleted_at");
            var roleModel = new RoleModel(id, code, name, description, createdBy, status, createdAt, lastUpdatedAt, deletedAt);
            roleModelList.add(roleModel);

        }
        return roleModelList;
    }

    public List<PrivilegeModel> selectPrivileges() throws SQLException {

        List<PrivilegeModel> privilegeModelList = new ArrayList<>();
        String privilegeTableName = TableNameGiver.getPrivilegeTableName();
        String tableFQN = tableSchema != null ? String.format("%s.%s", tableSchema.getName(), privilegeTableName) : privilegeTableName;
        String query = String.format("SELECT * FROM %s;", tableFQN);
        System.out.println(query);
        ResultWrapper resultWrapper = queryExecutor.executeQuery(query);
        ResultSet resultSet = resultWrapper.getResultSet();

        while (resultSet.next()) {

            String id = resultSet.getString("id");
            String code = resultSet.getString("code");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String approvalMethod = resultSet.getString("approval_method");
            String status = resultSet.getString("status");
            String createdBy = resultSet.getString("created_by");
            String createdAt = resultSet.getString("created_at");
            String lastUpdatedAt = resultSet.getString("last_updated_at");
            String deletedAt = resultSet.getString("deleted_at");
            var privilegeModel = new PrivilegeModel(id, code, name, description, approvalMethod, createdBy, status, createdAt, lastUpdatedAt, deletedAt);
            privilegeModelList.add(privilegeModel);

        }
        return privilegeModelList;
    }


}
