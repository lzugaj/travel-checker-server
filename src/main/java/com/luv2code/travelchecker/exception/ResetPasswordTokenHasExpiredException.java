package com.luv2code.travelchecker.exception;

public class ResetPasswordTokenHasExpiredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResetPasswordTokenHasExpiredException(final String message) {
        super(message);
    }

    public ResetPasswordTokenHasExpiredException(final String message,
                                                 final Throwable cause) {
        super(message, cause);
    }
}
