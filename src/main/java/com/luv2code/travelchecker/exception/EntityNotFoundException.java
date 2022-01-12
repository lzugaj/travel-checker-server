package com.luv2code.travelchecker.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(final String message) {
        super(message);
    }

    public EntityNotFoundException(final String message,
                                   final Throwable cause) {
        super(message, cause);
    }
}