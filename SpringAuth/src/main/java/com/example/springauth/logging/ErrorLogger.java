package com.example.springauth.logging;


public class ErrorLogger extends AbstractLogger{


    protected LoggerLevel loggerLevel;


    public ErrorLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, Target target){
        return;
    }

}
