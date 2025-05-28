package com.example.springauth.logging.targets;

public class ConsoleLogger implements ILogObserver {

    @Override
    public void log(String message){
        System.out.println("logging to the console: " + message);
        return;
    }
}
