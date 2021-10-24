package com.luv2code.travelchecker.exception;

public class SendEmailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SendEmailException(final String message) {
        super(message);
    }

    public SendEmailException(final String message,
                                        final Throwable cause) {
        super(message, cause);
    }
}
