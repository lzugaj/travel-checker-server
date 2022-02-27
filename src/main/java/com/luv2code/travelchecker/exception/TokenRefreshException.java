package com.luv2code.travelchecker.exception;

public class TokenRefreshException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenRefreshException(final String message) {
        super(message);
    }

    public TokenRefreshException(final String message,
                              final Throwable cause) {
        super(message, cause);
    }
}
