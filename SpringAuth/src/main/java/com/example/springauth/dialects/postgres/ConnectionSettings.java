package com.example.springauth.dialects.postgres;

public class ConnectionSettings {

    // Should be loaded securely eventually
    String databaseName = "standardization";
    String jdbcUrl = "jdbc:postgresql://localhost:5432/" + databaseName;
    String username = "postgres";
    String password = "evolve";

    private static ConnectionSettings connectionSettings;

    private ConnectionSettings() {

    }

    public static ConnectionSettings getConnectionSettings() {
        if (connectionSettings == null) {
            connectionSettings = new ConnectionSettings();
        }
        return connectionSettings;
    }

    // Still possible to create as many query executors as we want
    // If this becomes an issue, make the query executor class a singleton as well
    public QueryExecutor getQueryExecutor() {
        DatabaseCredentials databaseCredentials = new DatabaseCredentials(jdbcUrl, username, password);
        return new QueryExecutor(databaseCredentials);
    }
}
