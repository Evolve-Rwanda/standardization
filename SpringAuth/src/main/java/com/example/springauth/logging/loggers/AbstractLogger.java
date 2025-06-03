package com.example.springauth.logging.loggers;


import com.example.springauth.logging.targets.LoggerTarget;

public abstract class AbstractLogger {


    protected LoggerLevel loggerLevel;
    private AbstractLogger nextLevelLogger;


    public void setNextLevelLogger(AbstractLogger nextLevelLogger){
        this.nextLevelLogger = nextLevelLogger;
    }

    public void log(LoggerLevel loggerLevel, String message, LoggerTarget target){
        if (this.loggerLevel.equals(loggerLevel)){
            this.log(message, target);
        }
        if (nextLevelLogger != null){
            nextLevelLogger.log(loggerLevel, message, target);
        }
    }

    protected abstract void log(String message, LoggerTarget target);

}
