package com.maqoor.telegram_bot.exceptions;

public class LoginFailureAttemptException extends RuntimeException {
    public LoginFailureAttemptException(String message) {
        super(message);
    }
}
