package com.luv2code.travelchecker.exception;

public class PasswordNotConfirmedRightException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PasswordNotConfirmedRightException(final String message) {
        super(message);
    }

    public PasswordNotConfirmedRightException(final String message,
                                              final Throwable cause) {
        super(message, cause);
    }
}
