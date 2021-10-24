package com.luv2code.travelchecker.util;

import com.luv2code.travelchecker.domain.enums.RoleType;

public class SecurityConstants {

    public static final String SECRET = "m5jKHPO7Z2aqWYPdujYeoCnPNiUmgTRlfIBqCwU8HpRQ3fZQ55uk6jRPeA6pWkkVhSWXycBhD1zAI18VeebwKVzfYPoy70UCaMA9k2OhJ2x7Ldo1b5kxVpxV8BvcbrzWTWXdbS7FpKKep7TAI7KEvOLs4lMPJ7HWbcCWzwHoWP9dPe2hvUHE9C2ZEQHnG5MKMInfqptK3OS7xKDAgsoQrFmqpqXYUrfbThOqDTGr8kfWsOn2TyaENGkZsHI6CZOi";
    public static final long EXPIRATION_TIME = 31_556_952_000L; // 1 year in milliseconds
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 86_400_000; // 1 day in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    public static final Integer BCRYPT_ROUNDS = 12;

    public static final String AUTHENTICATION_URL = "/authentication";
    public static final String AUTHORIZATION_URL =  "/authorization";
    public static final String AUTH_ME_URL =  "/auth/me";
    public static final String USERS_URL = "/users/**";
    public static final String PROFILES_UPDATE_ME_URL = "/profiles/me";
    public static final String MARKERS_URL = "/markers/**";
    public static final String MAPBOX_URL = "/mapbox/**";
    public static final String FORGOT_PASSWORD_URL = "/forgot-password";
    public static final String RESET_PASSWORD_URL = "/reset-password**";

    public static final String ADMIN_ROLE = RoleType.ADMIN.name();
    public static final String USER_ROLE = RoleType.USER.name();

}
