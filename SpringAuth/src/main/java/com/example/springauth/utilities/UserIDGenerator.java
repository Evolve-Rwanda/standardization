package com.example.springauth.utilities;

import java.util.Random;

public class UserIDGenerator {
    public static String generateUserID() {
        return String.format(
                "USER_%s_%s",
                DateTime.getTimeStamp(),
                (new Random()).nextLong(10, 1_000_000)
        );
    }
}
