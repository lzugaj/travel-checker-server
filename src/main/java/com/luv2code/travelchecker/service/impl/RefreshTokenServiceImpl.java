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
import java.time.Instant;
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
                    LOGGER.error("Refresh token is not in database!");
                    throw new TokenRefreshException("Refresh token is not in database!");
                });

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        final String jwtToken = JwtUtil.generateToken(userDetails, jwtProperties.getSecret());
        LOGGER.info("Authentication success for user with id: ´{}´.", user.getId());
        response.addHeader("access-token", "Bearer " + jwtToken);
        response.addHeader("refresh-token", token.toString());
    }

    @Override
    public RefreshToken create(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME));
        refreshToken.setToken(UUID.randomUUID());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(final RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException("Refresh token was expired. Please make a new login request");
        }

        return refreshToken;
    }
}
