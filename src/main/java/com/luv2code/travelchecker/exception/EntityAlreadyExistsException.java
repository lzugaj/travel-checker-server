package com.luv2code.travelchecker.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(final String message) {
        super(message);
    }
}
