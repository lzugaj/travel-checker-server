package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.RefreshToken;
import com.luv2code.travelchecker.domain.User;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public interface RefreshTokenService {

    void findByToken(final UUID token, final HttpServletResponse response);

    RefreshToken create(final User user);

    RefreshToken verifyExpiration(final RefreshToken refreshToken);

}
