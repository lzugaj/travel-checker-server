package com.luv2code.travelchecker.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.configuration.JwtProperties;
import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.dto.authentication.AuthenticationDto;
import com.luv2code.travelchecker.service.RefreshTokenService;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtProperties jwtProperties;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private ObjectMapper mapper;

    private AuthenticationDto authenticationDto;

    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();

        jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, userService, refreshTokenService, jwtProperties);
    }

    @Test
    public void should_Successfully_Attempt_Authentication() throws Exception {
        final Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "john.doe@gmail.com");
        requestMap.put("password", "#Password1234");

        final String json = mapper.writeValueAsString(requestMap);

        BDDMockito.given(request.getInputStream()).willReturn(
                new DelegatingServletInputStream(
                        new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));

        final Authentication authentication = Mockito.mock(Authentication.class);

        BDDMockito.given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("john.doe@gmail.com", "#Password1234")))
                .willReturn(authentication);

        final Authentication authenticationResult = jwtAuthenticationFilter.attemptAuthentication(request, response);

        Assertions.assertEquals(authenticationResult, authentication);

    }

    @Test
    public void should_Throw_Exception_When_Password_Is_Null() throws Exception {
        final Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "john.doe@gmail.com");

        final String json = mapper.writeValueAsString(requestMap);

        BDDMockito.given(request.getInputStream()).willReturn(
                new DelegatingServletInputStream(
                        new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));

        final Exception exception = Assertions.assertThrows(
                BadCredentialsException.class,
                () -> jwtAuthenticationFilter.attemptAuthentication(request, response)
        );

        final String actualMessage = "Email and/or password must not be null.";
        final String expectedMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Throw_Exception_When_Email_Is_Null() throws Exception {
        final Map<String, String> requestMap = new HashMap<>();
        requestMap.put("password", "#Password1234");

        final String json = mapper.writeValueAsString(requestMap);

        BDDMockito.given(request.getInputStream()).willReturn(
                new DelegatingServletInputStream(
                        new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));

        final Exception exception = Assertions.assertThrows(
                BadCredentialsException.class,
                () -> jwtAuthenticationFilter.attemptAuthentication(request, response)
        );

        final String actualMessage = "Email and/or password must not be null.";
        final String expectedMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
