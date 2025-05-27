package com.example.springauth.logging;

public class VerboseLogger extends AbstractLogger{


    protected LoggerLevel loggerLevel;


    public VerboseLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, Target target){
        return;
    }

}
