package com.example.springauth.utilities;

import java.util.Random;

public class SentSMSIdGenerator {
    public static String generateSMSId() {
        return String.format(
                "SMS_%s",
                UUIDGenerator.generateUUID()
        );
    }
}
