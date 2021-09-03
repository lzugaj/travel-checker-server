package com.luv2code.travelchecker.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.configuration.JwtConfiguration;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.authentication.AuthenticationDto;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final UserService userService;

    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager,
                                   final UserService userService,
                                   final JwtConfiguration jwtConfiguration) {
        setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) throws AuthenticationException {
        AuthenticationDto authenticationDto;
        try {
            authenticationDto = new ObjectMapper().readValue(request.getInputStream(), AuthenticationDto.class);
        } catch (IOException exception) {
            LOGGER.error("Authentication attempt with data which could not be parsed to AuthenticationRequest.");
            throw new BadCredentialsException("Data sent for authentication in could not be parsed.");
        }

        final String username = authenticationDto.getUsername();
        final String password = authenticationDto.getPassword();
        if (username == null || password == null) {
            LOGGER.error("Authentication attempt with incomplete data.");
            throw new BadCredentialsException("Username and password must not be null.");
        }

        try {
            return super.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final Exception exception) {
            if (!(exception instanceof EntityNotFoundException) &&
                    !(exception instanceof AuthenticationException)) {
                throw exception;
            }

            if (exception instanceof InternalAuthenticationServiceException) {
                LOGGER.info("Authentication attempt with unregistered username: ´{}´.", authenticationDto.getUsername());
            } else {
                User user = userService.findByUsername(username);
                LOGGER.error("Failed authentication attempt for user with id: ´{}´ using username: ´{}´.", user.getId(), authenticationDto.getUsername());
            }

            throw new BadCredentialsException("Invalid username or password.");
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authentication) {
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwtToken = JwtUtil.generateToken(userDetails, jwtConfiguration.getSecret());

        final User user = userService.findByUsername(userDetails.getUsername());
        LOGGER.info("Authentication success for user with id: ´{}´.", user.getId());
        response.addHeader(jwtConfiguration.getHeaderName(), jwtConfiguration.getTokenPrefix() + jwtToken);
    }
}
