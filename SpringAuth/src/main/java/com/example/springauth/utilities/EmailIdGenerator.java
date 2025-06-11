package com.example.springauth.utilities;

public class EmailIdGenerator {
    public static String generateEmailId() {
        return String.format(
                "EMAIL_%s",
                UUIDGenerator.generateUUID()
        );
    }
}
