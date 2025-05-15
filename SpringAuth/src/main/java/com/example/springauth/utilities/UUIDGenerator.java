package com.example.springauth.utilities;

import java.util.UUID;

public class UUIDGenerator {
    public static String generateRoleID() {
        return UUID.randomUUID().toString();
    }
}
