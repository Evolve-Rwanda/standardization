package com.example.springauth.logging.targets;

import com.example.springauth.logging.loggers.LoggerLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class LoggerTarget {


    private final Map<LoggerLevel, List<ILogObserver>> loggerLevelListMap;


    public LoggerTarget(){
        this.loggerLevelListMap = new HashMap<>();
    }

    public void addObserver(LoggerLevel loggerLevel, ILogObserver observer){
        if (!this.loggerLevelListMap.containsKey(loggerLevel)) {
            loggerLevelListMap.put(loggerLevel, new ArrayList<>());
        }
        loggerLevelListMap.get(loggerLevel).add(observer);
    }

    public void removeObserver(ILogObserver observer){
        for (List<ILogObserver> iLogObserverList: this.loggerLevelListMap.values()) {
            iLogObserverList.remove(observer);
        }
    }

    public void notifyAllObservers(LoggerLevel loggerLevel, String message){
        if (loggerLevelListMap.containsKey(loggerLevel)) {
            for (ILogObserver iLogObserver: loggerLevelListMap.get(loggerLevel)) {
                iLogObserver.setLevel(loggerLevel.getLevel());
                iLogObserver.log(message);
            }
        }
    }

}
