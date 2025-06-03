package com.example.springauth.logging.loggers;


import com.example.springauth.logging.targets.*;
import com.example.springauth.services.LogService;

import java.util.ArrayList;
import java.util.List;


public class LogManager {


    private static final List<ILogObserver> observerList;

    static {
        observerList = new ArrayList<>();
    }

    public static AbstractLogger chainLoggers(){

        // Avoid cycles when chaining loggers. The first logger cannot be connected to
        // the very last logger. Consider loggers A, B, and C. The loggers A and C
        // cannot be connected, otherwise, the result is a cycle that will lead to infinite
        // looping. Infinite looping has the power to bring down the application from normal
        // operation, besides bringing the logging itself down.
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
        observerList.add(consoleLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.INFO), consoleLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.ERROR), consoleLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.DEBUG), consoleLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.WARNING), consoleLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.VERBOSE), consoleLogger);

        FileLogger fileLogger = new FileLogger();
        observerList.add(fileLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.ERROR), fileLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.INFO), fileLogger);

        DatabaseLogger databaseLogger = new DatabaseLogger();
        observerList.add(databaseLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.ERROR), databaseLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.INFO), databaseLogger);
        loggerTarget.addObserver(new LoggerLevel(Level.WARNING), databaseLogger);

        return loggerTarget;
    }

    public static void setLogService(LogService logService) {
        for (ILogObserver iLogObserver: observerList) {
            iLogObserver.setLogService(logService);
        }
    }

}
