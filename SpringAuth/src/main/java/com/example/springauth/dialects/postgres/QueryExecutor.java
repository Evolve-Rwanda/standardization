package com.example.springauth.dialects.postgres;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class QueryExecutor {

    private List<ResultWrapper> resultWrapperList;
    private ResultWrapper resultWrapper;
    private final DatabaseCredentials databaseCredentials;

    public QueryExecutor(DatabaseCredentials databaseCredentials){
        this.databaseCredentials = databaseCredentials;
    }

    public void setResultWrapperList(List<ResultWrapper> resultWrapperList) {
        this.resultWrapperList = resultWrapperList;
    }

    public void setResultWrapper(ResultWrapper resultWrapper) {
        this.resultWrapper = resultWrapper;
    }

    private ResultWrapper runQuery(String query){
        ResultWrapper resultWrapper = null;
        try{
            String url = databaseCredentials.getUrl();
            String username = databaseCredentials.getUsername();
            String password = databaseCredentials.getPassword();
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultWrapper = new ResultWrapper(resultSet, connection, statement);
        }catch (SQLException e){
            e.getMessage();
        }
        return resultWrapper;
    }

    public List<ResultWrapper> executeQueryList(List<String> queryList){
        List<ResultWrapper> resultWrapperList = new ArrayList<>();
        for(String query: queryList) {
            ResultWrapper resultWrapper = this.runQuery(query);
            resultWrapperList.add(resultWrapper);
        }
        this.setResultWrapperList(resultWrapperList);
        return resultWrapperList;
    }

    public ResultWrapper executeQuery(String query){
        ResultWrapper resultWrapper = this.runQuery(query);
        this.setResultWrapper(resultWrapper);
        return resultWrapper;
    }

    public void closeResources(){
        if(resultWrapperList != null) {
            for (ResultWrapper rw : this.resultWrapperList)
                if(rw != null)
                    closeResource(rw);
        }
        if(resultWrapper != null)
            closeResource(resultWrapper);
    }

    private void closeResource(ResultWrapper rw){
        ResultSet resultSet = rw.getResultSet();
        Connection connection = rw.getConnection();
        Statement statement = rw.getStatement();
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
