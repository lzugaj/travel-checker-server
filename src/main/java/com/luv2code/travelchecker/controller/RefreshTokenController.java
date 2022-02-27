package com.luv2code.travelchecker.controller;

import com.luv2code.travelchecker.dto.refresh.RefreshTokenDto;
import com.luv2code.travelchecker.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(final RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody final RefreshTokenDto refreshToken,
                                          final HttpServletResponse response) {
        final UUID uuid = UUID.fromString(refreshToken.getRefreshToken());
        refreshTokenService.findByToken(uuid, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}