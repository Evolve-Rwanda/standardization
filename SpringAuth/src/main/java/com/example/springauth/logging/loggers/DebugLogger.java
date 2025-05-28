package com.example.springauth.logging.loggers;

import com.example.springauth.logging.targets.LoggerTarget;

public class DebugLogger extends AbstractLogger{


    protected LoggerLevel loggerLevel;


    public DebugLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, LoggerTarget target){
        target.notifyAllObservers((new LoggerLevel(Level.DEBUG)), message);
        return;
    }

}
