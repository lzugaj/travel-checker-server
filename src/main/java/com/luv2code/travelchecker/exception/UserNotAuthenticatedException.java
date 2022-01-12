package com.luv2code.travelchecker.exception;

public class UserNotAuthenticatedException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public UserNotAuthenticatedException(final String message) {
        super(message);
    }

    public UserNotAuthenticatedException(final String message,
                                         final Throwable cause) {
        super(message, cause);
    }
}
