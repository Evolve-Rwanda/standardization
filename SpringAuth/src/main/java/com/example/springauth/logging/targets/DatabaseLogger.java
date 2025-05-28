package com.example.springauth.logging.targets;

public class DatabaseLogger implements ILogObserver {

    @Override
    public void log(String message){
        // connect to the logging database, schema and table, and then log the message
        System.out.println("log to the database: " + message);
        return;
    }
}
