package com.example.springauth.dialects.postgres;

import com.example.springauth.columns.Column;
import com.example.springauth.documentation.DatabaseDocumentation;
import com.example.springauth.models.app.EntityPropModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.tables.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GeneralEntityPropSelector {


    String sqlDialect = "POSTGRES";
    QueryExecutor queryExecutor = ConnectionSettings.getConnectionSettings().getQueryExecutor();
    Schema databaseDocumentationSchema = SchemaSettings.getInstance().getSchema(SchemaNameGiver.getDocumentationSchemaName());
    DatabaseDocumentation databaseDocumentation;

    private String tableName;
    private List<EntityPropModel> entityPropModelList;
    private Map<String, String> fieldTypeMap;
    private Map<String, Boolean> typeNumericFlagMap;


    public GeneralEntityPropSelector(String tableName) {
        this.databaseDocumentation = new DatabaseDocumentation(
                sqlDialect,
                queryExecutor,
                databaseDocumentationSchema
        );
        this.tableName = tableName;
        this.entityPropModelList = new ArrayList<>();
        this.fieldTypeMap = new HashMap<>();
        this.typeNumericFlagMap = new HashMap<>();
        this.generate();
    }

    public List<EntityPropModel> getEntityPropModelList() {
        return entityPropModelList;
    }

    public void setEntityPropModelList(List<EntityPropModel> entityPropModelList) {
        this.entityPropModelList = entityPropModelList;
    }

    public Map<String, String> getFieldTypeMap() {
        return fieldTypeMap;
    }

    public void setFieldTypeMap(Map<String, String> fieldTypeMap) {
        this.fieldTypeMap = fieldTypeMap;
    }

    public Map<String, Boolean> getTypeNumericFlagMap() {
        return typeNumericFlagMap;
    }

    public void setTypeNumericFlagMap(Map<String, Boolean> typeNumericFlagMap) {
        this.typeNumericFlagMap = typeNumericFlagMap;
    }

    public void generate() {
        List<Table> tableList = this.databaseDocumentation.getTableList();
        List<Column> columnList = null;
        for (Table table : tableList) {
            if (table.getName().equalsIgnoreCase(this.tableName)) {
                columnList = table.getUniversalColumnList();
                break;
            }
        }
        if (columnList != null) {
            for (Column column : columnList) {
                String columnName = column.getName();
                String type = column.getDataType();
                this.entityPropModelList.add(new EntityPropModel(columnName, ""));
                this.fieldTypeMap.put(columnName, type);
                this.typeNumericFlagMap.put(columnName, PostgresType.isANumericType(type));
            }
        }
        return;
    }

}
