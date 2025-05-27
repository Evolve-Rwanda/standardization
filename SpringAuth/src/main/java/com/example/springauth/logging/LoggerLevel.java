package com.example.springauth.logging;

import java.util.Objects;

public class LoggerLevel {


    private int level;


    public LoggerLevel(int level){
        this.level = level;
    }

    @Override
    public String toString() {
        return "LoggerLevel{" + "level=" + level + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LoggerLevel that)) return false;
        return level == that.level;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
