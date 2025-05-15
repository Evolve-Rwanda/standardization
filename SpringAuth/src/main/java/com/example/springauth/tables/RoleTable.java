package com.example.springauth.tables;

import com.example.springauth.columns.Column;
import com.example.springauth.dialects.postgres.PostgresDialect;
import com.example.springauth.dialects.postgres.PostgresDialectInsertion;
import com.example.springauth.dialects.postgres.PostgresDialectSelection;
import com.example.springauth.models.app.RoleModel;

import java.sql.SQLException;
import java.util.List;

public class RoleTable extends Table {

    private final String name = TableNameGiver.getRoleTableName();

    public RoleTable(){
        super();
    }

    public RoleTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains information about all the " +
                "different roles users in the system or application can assume at a particular moment";
    }

    public RoleModel insertRole(RoleModel role){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialectInsertion postgresDialectInsertion = new PostgresDialectInsertion(queryExecutor, schema, name);
            return postgresDialectInsertion.insertRole(role);
        }
        return null;
    }

    public List<RoleModel> selectRoles() throws SQLException {
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            var selectionObj = new PostgresDialectSelection(queryExecutor, schema);
            return selectionObj.selectRoles();
        }
        return null;
    }
}
