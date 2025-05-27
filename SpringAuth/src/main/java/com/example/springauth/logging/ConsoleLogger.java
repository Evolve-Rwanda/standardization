package com.example.springauth.logging;

public class ConsoleLogger implements ILogObserver{

    @Override
    public void log(String message){
        System.out.println(message);
        return;
    }
}
