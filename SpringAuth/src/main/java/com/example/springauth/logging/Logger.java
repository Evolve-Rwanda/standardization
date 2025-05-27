package com.example.springauth.logging;


public class Logger {


    private static Logger logger;
    private static AbstractLogger chainOfLogger;


    private Logger(){
    }

    private static Logger getLogger(){
        if (logger == null){
            logger = new Logger();
            // Getting the very first logger object on the chain of loggers
            // In this case, this object is the info logger
            chainOfLogger = LogManager.chainLoggers();
        }
        return logger;
    }

    public void info(String message){
        this.createLog(new LoggerLevel(LoggerLevels.INFO), message);
    }

    public void error(String message){
      this.createLog(new LoggerLevel(LoggerLevels.ERROR), message);
    }

    public void debug(String message){
        this.createLog(new LoggerLevel(LoggerLevels.DEBUG), message);
    }

    public void warning(String message){
        this.createLog(new LoggerLevel(LoggerLevels.WARNING), message);
    }

    public void verbose(String message){
        this.createLog(new LoggerLevel(LoggerLevels.VERBOSE), message);
    }

    private void createLog(LoggerLevel loggerLevel, String message){
        chainOfLogger.log(loggerLevel, message, null);
    }

}
