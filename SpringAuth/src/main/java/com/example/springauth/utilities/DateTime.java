package com.example.springauth.utilities;

import java.util.Date;

public class DateTime {

    public static String getTimeStamp() {
        Date now = new Date();
        return new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(now);
    }

    public static String getDate() {
        Date now = new Date();
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(now);
    }
}
