package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.exception.UserNotAuthenticatedException;
import com.luv2code.travelchecker.service.AuthenticationService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public String getAuthenticatedUser() {
        final Authentication currentAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuthenticatedUser instanceof AnonymousAuthenticationToken) {
            throw new UserNotAuthenticatedException(
                    "User", "email", currentAuthenticatedUser.getName());
        } else {
            return currentAuthenticatedUser.getName();
        }
    }
}
