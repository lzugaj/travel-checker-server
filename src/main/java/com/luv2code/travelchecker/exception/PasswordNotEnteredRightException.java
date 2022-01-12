package com.luv2code.travelchecker.exception;

public class PasswordNotEnteredRightException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PasswordNotEnteredRightException(final String message) {
        super(message);
    }

    public PasswordNotEnteredRightException(final String message,
                                            final Throwable cause) {
        super(message, cause);
    }
}
