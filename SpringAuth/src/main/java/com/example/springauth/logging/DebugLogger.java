package com.example.springauth.logging;

public class DebugLogger extends AbstractLogger{


    protected LoggerLevel loggerLevel;


    public DebugLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, Target target){
        return;
    }

}
