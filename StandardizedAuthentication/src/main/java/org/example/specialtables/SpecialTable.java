package org.example.specialtables;

import org.example.dialects.postgres.QueryExecutor;
import org.example.dialects.postgres.ResultWrapper;
import org.example.schemas.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;



public class SpecialTable {


    protected String sqlDialect;
    protected QueryExecutor queryExecutor;
    protected Schema schema;
    protected String name;

    public SpecialTable(QueryExecutor queryExecutor, String sqlDialect, Schema schema) {
        this.queryExecutor = queryExecutor;
        this.sqlDialect = sqlDialect;
        this.schema = schema;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getFullyQualifiedName(){
        String schema = this.schema!= null ? this.schema.getName() : "";
        return !schema.isEmpty() ? (schema + "." + this.name) : this.name;
    }

    /* The SQL used in this table is the same for all SQL dialects and needs no special
       considerations for the dialect used */
    public static final class EntryChecker{

        private final QueryExecutor queryExecutor;
        private final String tableName;
        private final Schema schema;
        private final Map<String, String> fieldNameValueMap;

        public EntryChecker(QueryExecutor queryExecutor, String tableName, Schema schema, Map<String, String> fieldNameValueMap){
            this.queryExecutor = queryExecutor;
            this.tableName = tableName;
            this.schema = schema;
            this.fieldNameValueMap = fieldNameValueMap;
        }

        public boolean entryExists(){
            String schemaName = this.schema != null ? (schema.getName() + ".") : "";
            String dataTableName = schemaName + tableName;
            String whereClauseFilters = this.getFilterClause();
            String query = "SELECT COUNT(*) FROM " + dataTableName + " WHERE " + whereClauseFilters + ";";
            ResultWrapper resultWrapper = queryExecutor.executeQuery(query);
            if(resultWrapper == null)
                return false;
            ResultSet resultSet = resultWrapper.getResultSet();
            try {
                resultSet.next();
                int count = resultSet.getInt(1);
                return (count > 0);
            } catch (SQLException e) {
                System.out.println("Error checking for whether a schema exists." + e.getMessage());
            }finally {
                queryExecutor.closeResources();
            }
            return false;
        }

        private String getFilterClause(){
            StringBuilder clauseBuilder = new StringBuilder();
            int e = 0;
            for(String fieldName: fieldNameValueMap.keySet()){
                clauseBuilder.append(fieldName).append(" = ").append(fieldNameValueMap.get(fieldName));
                if((e + 1) < fieldNameValueMap.size())
                    clauseBuilder.append(" AND ");
                e++;
            }
            return clauseBuilder.toString();
        }
    }
}
