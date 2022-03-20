package com.luv2code.travelchecker.constants;

import com.luv2code.travelchecker.domain.enums.RoleType;

public class SecurityConstants {

    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 900_000L; // 15 minutes
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 604_800_000L; // 7 days
    public static final Long RESET_PASSWORD_EXPIRATION_TIME = 3_600_000L; // 1 hour

    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    public static final String SECRET = "m5jKHPO7Z2aqWYPdujYeoCnPNiUmgTRlfIBqCwU8HpRQ3fZQ55uk6jRPeA6pWkkVhSWXycBhD1zAI18VeebwKVzfYPoy70UCaMA9k2OhJ2x7Ldo1b5kxVpxV8BvcbrzWTWXdbS7FpKKep7TAI7KEvOLs4lMPJ7HWbcCWzwHoWP9dPe2hvUHE9C2ZEQHnG5MKMInfqptK3OS7xKDAgsoQrFmqpqXYUrfbThOqDTGr8kfWsOn2TyaENGkZsHI6CZOi";

    public static final String AUTHENTICATION_URL = "/authentication";
    public static final String AUTHORIZATION_URL =  "/authorization";
    public static final String AUTH_ME_URL =  "/auth/me";
    public static final String USERS_URL = "/users/**";
    public static final String PROFILES_UPDATE_ME_URL = "/profiles/me";
    public static final String MARKERS_URL = "/markers/**";
    public static final String MAPBOX_URL = "/mapbox/**";
    public static final String FORGOT_PASSWORD_URL = "/forgot-password";
    public static final String REFRESH_TOKEN_URL = "/refresh-token";

    // public static final String RESET_PASSWORD_URL = "/reset-password**";

    public static final String ADMIN_ROLE = RoleType.ADMIN.name();
    public static final String USER_ROLE = RoleType.USER.name();

}
