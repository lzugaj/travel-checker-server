package com.luv2code.travelchecker.exception;

public class UserNotAuthenticatedException extends RuntimeException {

    public UserNotAuthenticatedException(final String message) {
        super(message);
    }
}
