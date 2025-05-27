package com.example.springauth.logging;

public class LogManager {

    public static AbstractLogger chainLoggers(){
        AbstractLogger infoLogger = new InfoLogger(new LoggerLevel(LoggerLevels.INFO));
        AbstractLogger errorLogger = new ErrorLogger(new LoggerLevel(LoggerLevels.ERROR));
        AbstractLogger debugLogger = new DebugLogger(new LoggerLevel(LoggerLevels.DEBUG));
        AbstractLogger warningLogger = new WarningLogger(new LoggerLevel(LoggerLevels.WARNING));
        AbstractLogger verboseLogger = new VerboseLogger(new LoggerLevel(LoggerLevels.VERBOSE));
        infoLogger.setNextLevelLogger(errorLogger);
        errorLogger.setNextLevelLogger(debugLogger);
        debugLogger.setNextLevelLogger(warningLogger);
        warningLogger.setNextLevelLogger(verboseLogger);
        return infoLogger;
    }
}
