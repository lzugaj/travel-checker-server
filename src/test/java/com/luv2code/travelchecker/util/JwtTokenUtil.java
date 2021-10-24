package com.luv2code.travelchecker.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static com.luv2code.travelchecker.util.SecurityConstants.*;

public class JwtTokenUtil {

    public static final Collection<SimpleGrantedAuthority> userAuthorities = new ArrayList<>();
    static {
        userAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public static final Collection<SimpleGrantedAuthority> adminAuthorities = new ArrayList<>();
    static {
        adminAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public static String createUserToken(final String email) {
        final UserDetails userDetails = new User(email, "$2a$12$Gw9o/me9.BOeI5a40v7Reuxc5GyOdAMXUDWDnIWZFa6LM9HLeiyc6", userAuthorities);
        return buildJwtToken(email, userDetails);
    }

    public static String createAdminToken(final String email) {
        final UserDetails userDetails = new User(email, "$2a$12$WBG7PQLSfumuAHH0vUlkbuuHKLRrhYeLJ1d3FIvitkFKvuLuGX47u", adminAuthorities);
        return buildJwtToken(email, userDetails);
    }

    public static String createResetPasswordToken(final String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + PASSWORD_RESET_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    private static String buildJwtToken(final String email, final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
