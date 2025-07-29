package com.maqoor.telegram_bot.exceptions;

public class InvalidDataException  extends RuntimeException {
    public InvalidDataException (String message) {
        super(message);
    }
}
