package com.example.springauth.logging;

import java.util.List;
import java.util.Map;



public class LoggerTarget {


    private Map<LoggerLevel, List<ILogObserver>> loggerLevelListMap;

    public void addObserver(LoggerLevel loggerLevel, ILogObserver observer){
        return;
    }

    public void removeObserver(ILogObserver observer){
        return;
    }

    public void notifyAllObservers(LoggerLevel loggerLevel, String message){
        return;
    }

}
