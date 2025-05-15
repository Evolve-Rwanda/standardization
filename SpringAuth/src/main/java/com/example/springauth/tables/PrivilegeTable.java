package com.example.springauth.tables;

import com.example.springauth.columns.Column;
import com.example.springauth.dialects.postgres.PostgresDialectInsertion;
import com.example.springauth.dialects.postgres.PostgresDialectSelection;
import com.example.springauth.models.app.PrivilegeModel;
import com.example.springauth.models.app.RoleModel;

import java.sql.SQLException;
import java.util.List;

public class PrivilegeTable extends Table {

    private final String name = TableNameGiver.getPrivilegeTableName();

    public PrivilegeTable(){
        super();
    }

    public PrivilegeTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains information about all the " +
                "different privileges a given role in the system or application has. " +
                "For example, only a super admin has the privilege to add other admins";
    }

    public PrivilegeModel insertPrivilege(PrivilegeModel privilegeModel){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialectInsertion postgresDialectInsertion = new PostgresDialectInsertion(queryExecutor, schema, name);
            return postgresDialectInsertion.insertPrivilege(privilegeModel);
        }
        return null;
    }

    public List<PrivilegeModel> selectPrivileges() throws SQLException {
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            var selectionObj = new PostgresDialectSelection(queryExecutor, schema);
            return selectionObj.selectPrivileges();
        }
        return null;
    }
}
