package com.luv2code.travelchecker.exception;

public class EntityAlreadyExistsException extends AbstractEntityException {

    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistsException(final String entityName,
                                        final String fieldName,
                                        final String fieldValue) {
        this(entityName, fieldName, fieldValue, null);
    }

    public EntityAlreadyExistsException(final String entityName,
                                        final String fieldName,
                                        final String fieldValue,
                                        final Throwable cause) {
        super(entityName, fieldName, fieldValue, createMessage(entityName, fieldName, fieldValue), cause);
    }

    private static String createMessage(final String entityName, final String fieldName, final String fieldValue) {
        return String.format("Entity '%s' with '%s' value '%s' already exists.",
                entityName, fieldName, fieldValue);
    }
}
