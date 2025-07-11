package com.example.springauth.logging.loggers;


import com.example.springauth.logging.targets.LoggerTarget;

public class ErrorLogger extends AbstractLogger{

    public ErrorLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, LoggerTarget target) {
        target.notifyAllObservers((new LoggerLevel(Level.ERROR)), message);
    }

}
