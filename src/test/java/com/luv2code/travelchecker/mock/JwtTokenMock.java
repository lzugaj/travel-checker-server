package com.luv2code.travelchecker.mock;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class JwtTokenMock {

    private static final long PASSWORD_RESET_EXPIRATION_TIME = 2_100_000L; // 35 minutes
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 604_800_000L; // 7 days

    public static final String SECRET = "m5jKHPO7Z2aqWYPdujYeoCnPNiUmgTRlfIBqCwU8HpRQ3fZQ55uk6jRPeA6pWkkVhSWXycBhD1zAI18VeebwKVzfYPoy70UCaMA9k2OhJ2x7Ldo1b5kxVpxV8BvcbrzWTWXdbS7FpKKep7TAI7KEvOLs4lMPJ7HWbcCWzwHoWP9dPe2hvUHE9C2ZEQHnG5MKMInfqptK3OS7xKDAgsoQrFmqpqXYUrfbThOqDTGr8kfWsOn2TyaENGkZsHI6CZOi";

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

    /*public static String createAdminToken(final String email) {
        final UserDetails userDetails = new User(email, "$2a$12$WBG7PQLSfumuAHH0vUlkbuuHKLRrhYeLJ1d3FIvitkFKvuLuGX47u", adminAuthorities);
        return buildJwtToken(email, userDetails);
    }*/

    public static String generateResetPasswordToken(final String username, final String secretKey) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + PASSWORD_RESET_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static String generateResetPasswordTokenThatIsExpired(final String username, final String secretKey) {
        final Clock clock = Clock.fixed(
                Instant.parse("2021-01-12T10:05:22.635Z"),
                ZoneId.of("Europe/Zagreb"));
        final long expiration = clock.millis();

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(expiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private static String buildJwtToken(final String email, final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
