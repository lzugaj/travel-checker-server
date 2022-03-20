package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.exception.UserNotAuthenticatedException;
import com.luv2code.travelchecker.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private Authentication authentication;
    private AnonymousAuthenticationToken anonymousAuthenticationToken;
    private SecurityContext securityContext;

    @BeforeEach
    public void setup() {
        authentication = Mockito.mock(Authentication.class);
        anonymousAuthenticationToken = Mockito.mock(AnonymousAuthenticationToken.class);
        securityContext = Mockito.mock(SecurityContext.class);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void should_Return_Authenticated_User() {
        BDDMockito.given(securityContext.getAuthentication()).willReturn(authentication);
        BDDMockito.given(authentication.getName()).willReturn("user@test.net");

        final String authenticatedUser = authenticationService.getAuthenticatedEmail();

        Assertions.assertEquals("user@test.net", authenticatedUser);
    }

    @Test
    public void should_Throw_Exception_When_User_Is_Not_Authenticated() {
        BDDMockito.given(securityContext.getAuthentication()).willReturn(anonymousAuthenticationToken);

        final Exception exception = Assertions.assertThrows(
                UserNotAuthenticatedException.class,
                () -> authenticationService.getAuthenticatedEmail()
        );

        final String expectedMessage = "Request is rejected because user is not authenticated.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
