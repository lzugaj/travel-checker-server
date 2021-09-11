package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    public void setup() {
        final Authentication authentication = Mockito.mock(Authentication.class);
        final SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        SecurityContextHolder.setContext(securityContext);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("user@test.net");
    }

    @Test
    public void should_Return_Authenticated_User() {
        final String authenticatedUser = authenticationService.getAuthenticatedEmail();

        Assertions.assertEquals("user@test.net", authenticatedUser);
    }
}
