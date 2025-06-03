package com.example.springauth.logging.loggers;

import com.example.springauth.logging.targets.LoggerTarget;

public class VerboseLogger extends AbstractLogger{


    public VerboseLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, LoggerTarget target) {
        target.notifyAllObservers((new LoggerLevel(Level.VERBOSE)), message);
    }

}
