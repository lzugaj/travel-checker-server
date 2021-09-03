package com.luv2code.travelchecker.exception;

public class UserNotAuthenticatedException extends AbstractEntityException {

    public static final long serialVersionUID = 1L;

    public UserNotAuthenticatedException(final String entityName,
                                         final String fieldName,
                                         final String fieldValue) {
        this(entityName, fieldName, fieldValue, null);
    }

    public UserNotAuthenticatedException(final String entityName,
                                         final String fieldName,
                                         final String fieldValue,
                                         final Throwable cause) {
        super(entityName, fieldName, fieldValue, createMessage(entityName, fieldName, fieldValue), cause);
    }

    private static String createMessage(final String entityName, final String fieldName, final String fieldValue) {
        return String.format("Entity '%s' with '%s' value '%s' is not authenticated.",
                entityName, fieldName, fieldValue);
    }
}
