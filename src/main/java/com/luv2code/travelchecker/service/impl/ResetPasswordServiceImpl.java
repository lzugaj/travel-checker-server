package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.configuration.JwtConfiguration;
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

    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public ResetPasswordServiceImpl(final UserService userService,
                                    final PasswordEncoder passwordEncoder,
                                    final JwtConfiguration jwtConfiguration) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public void resetPassword(final String token, final ResetPasswordDto resetPasswordDto) {
        if (isTokenExpired(token)) {
            LOGGER.error("Reset password token has expired.");
            throw new ResetPasswordTokenHasExpiredException("Reset password token has expired.");
        }

        if (!arePasswordsEquals(resetPasswordDto)) {
            LOGGER.error("Password is not confirmed right.");
            throw new PasswordNotConfirmedRightException("Password is not confirmed right while resetting password.");
        }

        final String subject = JwtUtil.extractUsername(token, jwtConfiguration.getSecret());
        final User searchedUser = userService.findByEmail(subject);
        LOGGER.info("Successfully founded User with id: ´{}´.", searchedUser.getId());

        searchedUser.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userService.update(subject, searchedUser);
        LOGGER.info("Successfully updated password for User with id: ´{}´.", searchedUser.getId());
    }

    private boolean isTokenExpired(final String token) {
        final Date expirationDate = JwtUtil.extractExpiration(token, jwtConfiguration.getSecret());
        return expirationDate.before(new Date());
    }

    private boolean arePasswordsEquals(final ResetPasswordDto resetPasswordDto) {
        return resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmedNewPassword());
    }
}
