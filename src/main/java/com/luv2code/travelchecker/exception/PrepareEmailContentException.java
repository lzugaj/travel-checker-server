package com.luv2code.travelchecker.exception;

public class PrepareEmailContentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PrepareEmailContentException(final String message) {
        super(message);
    }

    public PrepareEmailContentException(final String message,
                                        final Throwable cause) {
        super(message, cause);
    }
}
