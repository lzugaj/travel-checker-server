package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.configuration.JwtProperties;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.password.ResetPasswordDto;
import com.luv2code.travelchecker.exception.PasswordNotConfirmedRightException;
import com.luv2code.travelchecker.exception.ResetPasswordTokenHasExpiredException;
import com.luv2code.travelchecker.service.ResetPasswordService;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordServiceImpl.class);

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtProperties jwtProperties;

    @Autowired
    public ResetPasswordServiceImpl(final UserService userService,
                                    final PasswordEncoder passwordEncoder,
                                    final JwtProperties jwtProperties) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void resetPassword(final String token, final ResetPasswordDto resetPasswordDto) {
        LOGGER.info("Begin process of updating User password.");
        if (isTokenExpired(token)) {
            LOGGER.error("Reset password token has expired.");
            throw new ResetPasswordTokenHasExpiredException("Reset password token has expired.");
        }

        if (!arePasswordsEquals(resetPasswordDto)) {
            LOGGER.error("Password is not confirmed right while resetting password.");
            throw new PasswordNotConfirmedRightException("Password is not confirmed right while resetting password.");
        }

        final String email = JwtUtil.extractUsername(token, jwtProperties.getSecret());
        LOGGER.debug("Successfully extracted email from JWT token.");

        final User searchedUser = userService.findByEmail(email);
        LOGGER.debug("Successfully founded searched User. [id={}]", searchedUser.getId());

        searchedUser.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userService.update(email, searchedUser);
    }

    private boolean isTokenExpired(final String token) {
        LOGGER.debug("Checking if given token has expired.");
        final Date expirationDate = JwtUtil.extractExpiration(token, jwtProperties.getSecret());
        LOGGER.debug("Extract expiration date from JWT token.");
        return expirationDate.before(new Date());
    }

    private boolean arePasswordsEquals(final ResetPasswordDto resetPasswordDto) {
        LOGGER.debug("Checking are given passwords equal.");
        return resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmedNewPassword());
    }
}
