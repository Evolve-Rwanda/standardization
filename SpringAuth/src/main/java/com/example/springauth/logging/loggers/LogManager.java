package com.example.springauth.logging.loggers;


import com.example.springauth.logging.targets.ConsoleLogger;
import com.example.springauth.logging.targets.FileLogger;
import com.example.springauth.logging.targets.LoggerTarget;


public class LogManager {


    public static AbstractLogger chainLoggers(){

        AbstractLogger infoLogger = new InfoLogger(new LoggerLevel(Level.INFO));
        AbstractLogger errorLogger = new ErrorLogger(new LoggerLevel(Level.ERROR));
        AbstractLogger debugLogger = new DebugLogger(new LoggerLevel(Level.DEBUG));
        AbstractLogger warningLogger = new WarningLogger(new LoggerLevel(Level.WARNING));
        AbstractLogger verboseLogger = new VerboseLogger(new LoggerLevel(Level.VERBOSE));

        infoLogger.setNextLevelLogger(errorLogger);
        errorLogger.setNextLevelLogger(debugLogger);
        debugLogger.setNextLevelLogger(warningLogger);
        warningLogger.setNextLevelLogger(verboseLogger);

        return infoLogger;
    }

    public static LoggerTarget addObservers(){

        LoggerTarget loggerTarget = new LoggerTarget();

        ConsoleLogger consoleLogger = new ConsoleLogger();
        loggerTarget.addObserver(new LoggerLevel(Level.INFO), consoleLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.ERROR), consoleLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.DEBUG), consoleLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.WARNING), consoleLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.VERBOSE), consoleLogger);

        FileLogger fileLogger = new FileLogger();
        loggerTarget.addObserver(new LoggerLevel(Level.ERROR), fileLogger);

        return loggerTarget;
    }

}
