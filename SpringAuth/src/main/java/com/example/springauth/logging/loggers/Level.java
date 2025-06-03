package com.example.springauth.logging.loggers;

import java.util.HashMap;
import java.util.Map;


public class Level {


    public static int INFO = 1;
    public static int ERROR = 2;
    public static int DEBUG = 3;
    public static int WARNING = 4;
    public static int VERBOSE = 5;
    public static Map<Integer, String> levelNameMap;

    static{
        levelNameMap = new HashMap<>();
        levelNameMap.put(INFO, "INFO");
        levelNameMap.put(ERROR, "ERROR");
        levelNameMap.put(DEBUG, "DEBUG");
        levelNameMap.put(WARNING, "WARNING");
        levelNameMap.put(VERBOSE, "VERBOSE");
    }

    public static String getName(int level) {
        return levelNameMap.get(level);
    }
}
