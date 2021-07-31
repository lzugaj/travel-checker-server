package com.luv2code.travelchecker.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractEntityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String fieldName;

    private final String fieldValue;

    public AbstractEntityException(final String entityName,
                                   final String fieldName,
                                   final String fieldValue,
                                   final String message,
                                   final Throwable cause) {
        super(message, cause);
        this.entityName = entityName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}