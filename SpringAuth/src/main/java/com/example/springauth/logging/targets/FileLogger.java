package com.example.springauth.logging.targets;

public class FileLogger implements ILogObserver {

    @Override
    public void log(String message){
        // append to a file on a new line
        System.out.println("appended to file: " + message);
        return;
    }
}
