package com.luv2code.travelchecker.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistsException(final String message) {
        super(message);
    }

    public EntityAlreadyExistsException(final String message,
                                        final Throwable cause) {
        super(message, cause);
    }
}
