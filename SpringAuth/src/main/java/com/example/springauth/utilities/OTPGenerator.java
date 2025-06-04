package com.example.springauth.utilities;

import java.util.Random;

public class OTPGenerator {
    public static int generateOTP() {
        return new Random().nextInt(1000, 9999);
    }
    public static int generateLongOTP() {
        return new Random().nextInt(100_000, 999999);
    }
}
