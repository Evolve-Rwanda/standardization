package com.example.springauth.logging;


public class WarningLogger extends AbstractLogger{


    protected LoggerLevel loggerLevel;


    public WarningLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, Target target){
        return;
    }

}
