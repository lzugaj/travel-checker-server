package com.luv2code.travelchecker.service;

public interface MailService {

    void sendPasswordResetRequest(final String firstName, final String email, final String resetToken);

}
