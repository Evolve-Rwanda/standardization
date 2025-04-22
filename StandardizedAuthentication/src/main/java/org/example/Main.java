package org.example;


public class Main {

    public static void main(String[] args) {
        StandardizationManager standardizationManager = StandardizationManager.getStandardizationManager();
        standardizationManager.initializeStandardAuthModule();
    }
}