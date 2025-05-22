package com.example.springauth.dialects.postgres;

import com.example.springauth.models.app.PwdChangeModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.tables.TableNameGiver;

import java.sql.ResultSet;

public class PostgresUpdate {


    private final Schema tableSchema;
    private final QueryExecutor queryExecutor;


    public PostgresUpdate(QueryExecutor queryExecutor, Schema tableSchema){
        this.queryExecutor = queryExecutor;
        this.tableSchema = tableSchema;
    }

    public boolean updatePassword(String username, String password){
        String setPathQuery = this.getSchemaPathQuery();
        String query = String.format(
                "UPDATE %s SET current_password = :currentPassword WHERE current_email = :currentEmail;",
                TableNameGiver.getUserTableName(), password, username
        );
        ResultWrapper resultWrapper = queryExecutor.executeQuery(setPathQuery + query);
        ResultSet resultSet = resultWrapper.getResultSet();
        return true;
    }

    private String getSchemaPathQuery(){
        return String.format("SET search_path TO %s;", this.tableSchema.getName());
    }

}
