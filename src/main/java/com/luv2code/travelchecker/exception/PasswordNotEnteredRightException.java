package com.luv2code.travelchecker.exception;

public class PasswordNotEnteredRightException extends AbstractEntityException {

    private static final long serialVersionUID = 1L;

    public PasswordNotEnteredRightException(final String entityName,
                                              final String fieldName,
                                              final String fieldValue) {
        this(entityName, fieldName, fieldValue, null);
    }

    public PasswordNotEnteredRightException(final String entityName,
                                            final String fieldName,
                                            final String fieldValue,
                                            final Throwable cause) {
        super(entityName, fieldName, fieldValue, createMessage(entityName, fieldName, fieldValue), cause);
    }

    private static String createMessage(final String entityName, final String fieldName, final String fieldValue) {
        return String.format("Entity '%s' with '%s' value '%s' is not entered right.",
                entityName, fieldName, fieldValue);
    }
}
