package com.example.springauth.utilities.security.encryption;

import jakarta.persistence.AttributeConverter;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

public class StringEncryptorDecrypter implements AttributeConverter<String, String>{
    @Autowired
    private StringEncryptor encryptor;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return encryptor.decrypt(dbData);
    }
}