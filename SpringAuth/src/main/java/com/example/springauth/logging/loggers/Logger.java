package com.example.springauth.logging.loggers;


import com.example.springauth.logging.targets.LoggerTarget;

public class Logger {


    private static Logger logger;
    private static AbstractLogger chainOfLogger;
    private static LoggerTarget loggerTarget;


    private Logger(){
    }

    private static Logger getLogger(){
        if (logger == null){
            logger = new Logger();
            // Getting the very first logger object on the chain of loggers
            // In this case, this object is the info logger
            chainOfLogger = LogManager.chainLoggers();
            loggerTarget = LogManager.addObservers();
        }
        return logger;
    }

    public void info(String message){
        this.log(new LoggerLevel(Level.INFO), message);
    }

    public void error(String message){
      this.log(new LoggerLevel(Level.ERROR), message);
    }

    public void debug(String message){
        this.log(new LoggerLevel(Level.DEBUG), message);
    }

    public void warning(String message){
        this.log(new LoggerLevel(Level.WARNING), message);
    }

    public void verbose(String message){
        this.log(new LoggerLevel(Level.VERBOSE), message);
    }

    private void log(LoggerLevel loggerLevel, String message){
        chainOfLogger.log(loggerLevel, message, loggerTarget);
    }

}
