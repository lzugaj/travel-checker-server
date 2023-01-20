package com.luv2code.travelchecker.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.travelchecker.configuration.JwtProperties;
import com.luv2code.travelchecker.domain.RefreshToken;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.authentication.AuthenticationDto;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.service.RefreshTokenService;
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

    private final RefreshTokenService refreshTokenService;

    private final JwtProperties jwtProperties;

    @Autowired
    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager,
                                   final UserService userService,
                                   final RefreshTokenService refreshTokenService,
                                   final JwtProperties jwtProperties) {
        setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) throws AuthenticationException {
        AuthenticationDto authenticationDto;
        try {
            authenticationDto = new ObjectMapper().readValue(request.getInputStream(), AuthenticationDto.class);
        } catch (final IOException e) {
            LOGGER.error("Authentication attempt with data which could not be parsed to AuthenticationRequest.");
            throw new BadCredentialsException("Data sent for authentication in could not be parsed.");
        }

        final String email = authenticationDto.getEmail();
        final String password = authenticationDto.getPassword();
        if (email == null || password == null) {
            LOGGER.error("Authentication attempt with incomplete data.");
            throw new BadCredentialsException("Email and/or password must not be null.");
        }

        try {
            return super.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (final Exception e) {
            if (!(e instanceof EntityNotFoundException) && !(e instanceof AuthenticationException)) {
                throw e;
            }

            if (e instanceof InternalAuthenticationServiceException) {
                LOGGER.error("Authentication attempt with unregistered email: ´{}´.", authenticationDto.getEmail());
            } else {
                final User user = userService.findByEmail(email);
                LOGGER.error("Failed authentication attempt for user with id: ´{}´ using email: ´{}´.", user.getId(), authenticationDto.getEmail());
            }

            throw new BadCredentialsException("Invalid email or password.");
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authentication) {
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwtToken = JwtUtil.generateToken(userDetails, jwtProperties.getSecret());

        final User user = userService.findByEmail(userDetails.getUsername());
        final RefreshToken refreshToken = refreshTokenService.create(user);
        LOGGER.info("Authentication success for user with id: ´{}´.", user.getId());
        response.addHeader("access-token", String.format("Bearer %s", jwtToken));
        response.addHeader("refresh-token", String.valueOf(refreshToken.getToken()));
    }
}
