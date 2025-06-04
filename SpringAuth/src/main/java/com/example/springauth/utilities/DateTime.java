package com.example.springauth.utilities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;


public class DateTime {


    public static String getTimeStamp() {
        Date now = new Date();
        return new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(now);
    }

    public static String getTimeStampWithoutTimeZone() {
        Date now = new Date();
        return new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(now);
    }

    public static String getDate() {
        Date now = new Date();
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(now);
    }

    public static long getDurationInSeconds(String startTimeString, String endTimeString) {
        LocalDateTime start = getLocalDateTime(startTimeString);
        LocalDateTime end = getLocalDateTime(endTimeString);
        Duration duration = Duration.between(start, end);
        return duration.getSeconds();
    }

    public static long getDurationInMinutes(String startTimeString, String endTimeString) {
        LocalDateTime start = getLocalDateTime(startTimeString);
        LocalDateTime end = getLocalDateTime(endTimeString);
        Duration duration = Duration.between(start, end);
        return duration.toMinutes();
    }

    public static long getDurationInHours(String startTimeString, String endTimeString) {
        LocalDateTime start = getLocalDateTime(startTimeString);
        LocalDateTime end = getLocalDateTime(endTimeString);
        Duration duration = Duration.between(start, end);
        return duration.toHours();
    }

    public static long getDurationInDays(String startTimeString, String endTimeString) {
        LocalDateTime start = getLocalDateTime(startTimeString);
        LocalDateTime end = getLocalDateTime(endTimeString);
        Duration duration = Duration.between(start, end);
        return duration.toDays();
    }

    public static float getDurationInYears(String startTimeString, String endTimeString) {
        LocalDateTime start = getLocalDateTime(startTimeString);
        LocalDateTime end = getLocalDateTime(endTimeString);
        Duration duration = Duration.between(start, end);
        return ((float)duration.toDays() / 365f); // not always 365
    }

    private static LocalDateTime getLocalDateTime(String localDateTimeString) {
        return LocalDateTime.parse(localDateTimeString);
    }


}
