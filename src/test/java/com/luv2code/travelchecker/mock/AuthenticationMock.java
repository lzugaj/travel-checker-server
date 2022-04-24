package com.luv2code.travelchecker.mock;

import com.luv2code.travelchecker.dto.authentication.AuthenticationDto;

public class AuthenticationMock {

    public static AuthenticationDto createAuthentication(final String email, final String password) {
        final AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setEmail(email);
        authenticationDto.setPassword(password);
        return authenticationDto;
    }
}
