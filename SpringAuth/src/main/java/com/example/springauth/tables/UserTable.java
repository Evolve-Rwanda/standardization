package com.example.springauth.tables;

import com.example.springauth.columns.Column;
import com.example.springauth.dialects.postgres.PostgresDialectInsertion;
import com.example.springauth.dialects.postgres.PostgresDialectSelection;
import com.example.springauth.models.app.UserModel;

import java.sql.SQLException;
import java.util.List;


public class UserTable extends Table {

    private final String name = TableNameGiver.getUserTableName();

    public UserTable(){
        super();
    }

    public UserTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains information about all the different users in the system or application";
    }

    public UserModel insertUser(UserModel userModel){
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialectInsertion postgresDialectInsertion = new PostgresDialectInsertion(queryExecutor, schema, name);
            return postgresDialectInsertion.insertUser(userModel);
        }
        return null;
    }

    public List<UserModel> selectUsers() throws SQLException {
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            var selectionObj = new PostgresDialectSelection(queryExecutor, schema);
            return selectionObj.selectUsers();
        }
        return null;
    }

}
