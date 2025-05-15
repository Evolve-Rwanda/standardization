package com.example.springauth.utilities;

import java.util.Random;

public class PrivilegeIDGenerator {

    public static String generatePrivilegeID() {
        return String.format(
                "PRIVILEGE_%s_%s",
                DateTime.getTimeStamp(),
                (new Random()).nextLong(10_000, 1_000_000)
        );
    }
}
