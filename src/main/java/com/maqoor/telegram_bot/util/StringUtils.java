package com.maqoor.telegram_bot.util;

public class StringUtils {

    public static String tipExtract(String text) {

        if (text.isBlank() || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be blank or empty");
        }String lastElem = text.substring(text.length() - 1).trim();
        if(lastElem.equals("1") || lastElem.equals("2")) {
           text = text.substring(0, text.length() - 1) + "0";
           return text;
        }
        return text;
    }

    public static String removeZeroes(String text) {
        if (text.isEmpty() || text.isBlank()) {
            throw new IllegalArgumentException("Text cannot be blank or empty");
        }
        if(text.endsWith(".00")) {
            return text.substring(0, text.length() - 3);
        }
        return text;
    }

    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

}
