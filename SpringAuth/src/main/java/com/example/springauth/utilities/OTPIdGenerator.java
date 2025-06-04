package com.example.springauth.utilities;

import java.util.Random;

public class OTPIdGenerator {
    public static String generateOTPId() {
        return UUIDGenerator.generateUUID();
    }
}
