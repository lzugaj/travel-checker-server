package com.luv2code.travelchecker.exception;

public class ResetPasswordTokenHasExpiredException extends RuntimeException {

    public ResetPasswordTokenHasExpiredException(final String message) {
        super(message);
    }
}
