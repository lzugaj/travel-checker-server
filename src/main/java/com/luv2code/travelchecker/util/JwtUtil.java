package com.luv2code.travelchecker.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.luv2code.travelchecker.util.SecurityConstants.EXPIRATION_TIME;
import static com.luv2code.travelchecker.util.SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME;

public class JwtUtil {

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

    private static Boolean isTokenExpired(final String token, final String secretKey) {
        return extractExpiration(token, secretKey).before(new Date());
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
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static String generateResetPasswordToken(final String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + PASSWORD_RESET_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public static Boolean validateToken(final String token, final UserDetails userDetails, final String secretKey) {
        final String email = extractUsername(token, secretKey);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token, secretKey));
    }
}


