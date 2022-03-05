package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.configuration.JwtProperties;
import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.domain.RefreshToken;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.exception.TokenRefreshException;
import com.luv2code.travelchecker.repository.RefreshTokenRepository;
import com.luv2code.travelchecker.service.RefreshTokenService;
import com.luv2code.travelchecker.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsService userDetailsService;

    private final JwtProperties jwtProperties;

    public RefreshTokenServiceImpl(final RefreshTokenRepository refreshTokenRepository,
                                   final UserDetailsService userDetailsService,
                                   final JwtProperties jwtProperties) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userDetailsService = userDetailsService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void findByToken(final UUID token, final HttpServletResponse response) {
        final User user = refreshTokenRepository.findByToken(token)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> {
                    LOGGER.error("Refresh token is not founded in database.");
                    throw new TokenRefreshException("Refresh token is not founded in database.");
                });

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        LOGGER.debug("Founded searched User details.");

        final String jwtToken = JwtUtil.generateToken(userDetails, jwtProperties.getSecret());
        LOGGER.debug("Generated access token for User.");

        response.addHeader("access-token", SecurityConstants.BEARER_TOKEN_PREFIX + jwtToken);
        response.addHeader("refresh-token", String.valueOf(token));
    }

    @Override
    @Transactional
    public RefreshToken create(final User user) {
        LOGGER.info("Begin process of creating new refresh token for User. [id={}]", user.getId());
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(LocalDateTime.from(Instant.now().plusMillis(SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME)))
                .token(UUID.randomUUID())
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);
        LOGGER.debug("Created refresh token for User. [id={}]", user.getId());
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(final RefreshToken refreshToken) {
        LOGGER.debug("Verifying expiration date for refresh token.");
        if (refreshToken.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            LOGGER.debug("Delete refresh token for User. [id={}]", refreshToken.getUser().getId());
            LOGGER.error("Refresh token has expired for User. [id={}]", refreshToken.getUser().getId());
            throw new TokenRefreshException(
                    String.format("Refresh token has expired for User. [id=%s]", refreshToken.getUser().getId())
            );
        }

        return refreshToken;
    }
}
