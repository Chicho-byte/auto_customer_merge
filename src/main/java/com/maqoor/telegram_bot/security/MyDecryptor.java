package com.maqoor.telegram_bot.security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;


public class MyDecryptor {
    public static String decrypt(String encryptedString,String key) {
        StandardPBEStringEncryptor data = new StandardPBEStringEncryptor();
        data.setPassword(key);
        return data.decrypt(encryptedString);
    }

}
