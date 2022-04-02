package com.luv2code.travelchecker.util;

import com.luv2code.travelchecker.constants.SecurityConstants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    public static String extractUsername(final String token, final String secretKey) {
        return extractClaim(token, Claims::getSubject, secretKey);
    }

    public static Date extractExpiration(final String token, final String secretKey) {
        return extractClaim(token, Claims::getExpiration, secretKey);
    }

    public static <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver, final String secretKey) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(final String token, final String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public static String generateToken(final UserDetails userDetails, final String secretKey) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername(), secretKey);
    }

    private static String createToken(final Map<String, Object> claims, final String subject, final String secretKey) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static String generateResetPasswordToken(final String username, final String secretKey) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.PASSWORD_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static boolean validateJwtToken(final String token, final String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (final SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (final MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (final ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (final UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (final IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}


