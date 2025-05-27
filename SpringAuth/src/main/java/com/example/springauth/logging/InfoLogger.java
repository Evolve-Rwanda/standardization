package com.example.springauth.logging;

public class InfoLogger extends AbstractLogger{

    protected LoggerLevel loggerLevel;

    public InfoLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, Target target){
        return;
    }

}
