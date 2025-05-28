package com.example.springauth.logging.loggers;


import com.example.springauth.logging.targets.LoggerTarget;

public class WarningLogger extends AbstractLogger{


    protected LoggerLevel loggerLevel;


    public WarningLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, LoggerTarget target) {
        target.notifyAllObservers((new LoggerLevel(Level.WARNING)), message);
        return;
    }

}
