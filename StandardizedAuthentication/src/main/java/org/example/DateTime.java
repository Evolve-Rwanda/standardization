package org.example;

public class DateTime {
    public DateTime(){
    }
    public static String getTimeStamp(){
        java.util.Date now = new java.util.Date();
        return new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(now);
    }
}
