package com.example.springauth.logging.loggers;

import com.example.springauth.logging.targets.LoggerTarget;


public class InfoLogger extends AbstractLogger{


    public InfoLogger(LoggerLevel loggerLevel){
        this.loggerLevel = loggerLevel;
    }

    @Override
    protected void log(String message, LoggerTarget target) {
        target.notifyAllObservers(this.loggerLevel, message);
    }

}
