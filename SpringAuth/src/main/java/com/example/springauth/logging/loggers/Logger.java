package com.example.springauth.logging.loggers;

import com.example.springauth.logging.message.Message;
import com.example.springauth.logging.targets.LoggerTarget;
import com.example.springauth.services.LogService;



public class Logger {


    private static Logger logger;
    private static AbstractLogger chainOfLogger;
    private static LoggerTarget loggerTarget;

    public static String INFO_INIT = new Message("SYSTEM", "INIT", "Initializing the information logger").toJSON();
    public static String ERROR_INIT = new Message("SYSTEM", "INIT", "Initializing the error logger").toJSON();
    public static String DEBUG_INIT = new Message("SYSTEM", "INIT", "Initializing the debug logger").toJSON();
    public static String WARNING_INIT = new Message("SYSTEM", "INIT", "Initializing the warning logger").toJSON();
    public static String VERBOSE_INIT = new Message("SYSTEM", "INIT", "Initializing the verbose logger").toJSON();


    private Logger(){
    }

    public static Logger getLogger(){
        if (logger == null){
            logger = new Logger();
            // Getting the very first logger object on the chain of loggers
            // In this case, this object is the info logger
            chainOfLogger = LogManager.chainLoggers();
            // add observers has to be called before setting the log service
            // to avoid null-pointer exceptions further into the observer pattern
            loggerTarget = LogManager.addObservers();
        }
        return logger;
    }

    public void setLogService(LogService logService){
        if (logger != null) {
            // The log manager will inturn forward the log service to log targets
            // Only the database logger needs the repository, but we pass to all
            // targets to keep a uniform pattern and minimize defensive checks
            LogManager.setLogService(logService);
        }
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
