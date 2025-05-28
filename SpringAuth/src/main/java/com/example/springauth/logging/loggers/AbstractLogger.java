package com.example.springauth.logging.loggers;


import com.example.springauth.logging.targets.LoggerTarget;

public abstract class AbstractLogger {


    protected LoggerLevel loggerLevel;
    private AbstractLogger nextLevelLogger;


    public void setNextLevelLogger(AbstractLogger nextLevelLogger){
        this.nextLevelLogger = nextLevelLogger;
    }

    // This implementation works for all loggers without necessarily
    // overriding it in any subclass. Override it for any special
    // considerations
    public void log(LoggerLevel loggerLevel, String message, LoggerTarget target){
        if (this.loggerLevel == loggerLevel){
            //log(msg, target) // overloaded version
        }
        if (nextLevelLogger != null){
            nextLevelLogger.log(loggerLevel, message, target);
        }
    }

    // Must be overridden by all logger children/subclasses
    protected abstract void log(String message, LoggerTarget target);

}
