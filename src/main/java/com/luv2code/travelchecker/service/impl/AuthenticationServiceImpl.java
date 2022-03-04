package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.exception.UserNotAuthenticatedException;
import com.luv2code.travelchecker.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Override
    public String getAuthenticatedEmail() {
        final Authentication currentAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuthenticatedUser instanceof AnonymousAuthenticationToken) {
            LOGGER.warn("Request is rejected because user is not authenticated. [email={}]", currentAuthenticatedUser.getName());
            throw new UserNotAuthenticatedException(
                    String.format("Request is rejected because user is not authenticated. [email=%s]",
                            currentAuthenticatedUser.getName())
            );
        } else {
            LOGGER.debug("Found currently authenticated User.");
            return currentAuthenticatedUser.getName();
        }
    }
}
