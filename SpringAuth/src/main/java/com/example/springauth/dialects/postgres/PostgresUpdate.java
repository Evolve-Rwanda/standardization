package com.example.springauth.dialects.postgres;

import com.example.springauth.models.app.EntityPropModel;
import com.example.springauth.models.app.PwdChangeModel;
import com.example.springauth.models.app.UserModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.tables.TableNameGiver;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgresUpdate {


    private final Schema tableSchema;
    private final QueryExecutor queryExecutor;

    private Map<String, String> fieldNameTypeNameMap;
    private Map<String, Boolean> typeNameNumericFlagMap;

    {
        fieldNameTypeNameMap = new HashMap<>();
        typeNameNumericFlagMap = new HashMap<>();
    }


    public PostgresUpdate(QueryExecutor queryExecutor, Schema tableSchema){
        this.queryExecutor = queryExecutor;
        this.tableSchema = tableSchema;
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

    public UserModel updateUser(UserModel userModel){
        String setPathQuery = this.getSchemaPathQuery();
        String updateQuery = this.getUpdateUserQuery(userModel);
        queryExecutor.executeQuery(setPathQuery + updateQuery);
        //ResultWrapper resultWrapper =
        //ResultSet resultSet = resultWrapper.getResultSet();
        return userModel;
    }

    private String getUpdateUserQuery(UserModel userModel){

        StringBuilder columnValueBuilder = new StringBuilder();
        List<EntityPropModel> entityPropModelList = userModel.getUserPropModelList();
        String userId = userModel.getPropertyValue("id");
        int i=1;
        int size = entityPropModelList.size();

        for(EntityPropModel entityPropModel: entityPropModelList){
            boolean isNumeric = this.isFieldNumeric(entityPropModel.getName());
            String columnName = entityPropModel.getName();
            String columnValue = entityPropModel.getValue();

            if(!isNumeric) {
                columnValueBuilder
                    .append(columnName)
                    .append("=")
                    .append("'")
                    .append(columnValue)
                    .append("'");
            }else {
                columnValueBuilder
                    .append(columnName)
                    .append("=")
                    .append(columnValue);
            }
            if(i < size){
                columnValueBuilder.append(",");
            }
            i++;
        }
        return String.format(
                "UPDATE \"%s\" SET %s WHERE id = '%s';",
                TableNameGiver.getUserTableName(), columnValueBuilder.toString(), userId
        );
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


    private String getSchemaPathQuery(){
        return String.format("SET search_path TO %s;", this.tableSchema.getName());
    }

}
