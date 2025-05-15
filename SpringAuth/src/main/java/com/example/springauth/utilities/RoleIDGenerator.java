package com.example.springauth.utilities;

import java.util.Random;

public class RoleIDGenerator {

    public static String generateRoleID() {
        return String.format(
                "ROLE_%s_%s",
                DateTime.getTimeStamp(),
                (new Random()).nextLong(10_000, 1_000_000)
        );
    }
}
