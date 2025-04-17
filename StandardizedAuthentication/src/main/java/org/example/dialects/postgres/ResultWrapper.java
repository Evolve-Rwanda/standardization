package org.example.dialects.postgres;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public record ResultWrapper(ResultSet resultSet, Connection connection, Statement statement){
    @Override
    public String toString(){
        return resultSet.toString();
    }
    public ResultSet getResultSet(){
        return this.resultSet;
    }
    public Connection getConnection(){
        return this.connection;
    }
    public Statement getStatement(){
        return this.statement;
    }

}
