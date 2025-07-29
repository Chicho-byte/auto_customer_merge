package com.maqoor.telegram_bot.util;

import com.maqoor.telegram_bot.exceptions.InvalidDataException;

public class PhoneUtil {

    public static String formatPhoneNumber(String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) {
            throw new InvalidDataException("Phone number cannot be null or empty");
        }
        phoneNumber = phoneNumber.trim();
        if (phoneNumber.startsWith("374")) {
            return "0" + phoneNumber.substring(3);
        } else if(phoneNumber.startsWith("+")) {
            return "0" + phoneNumber.substring(4);
        }
        return phoneNumber;
    }


    }

