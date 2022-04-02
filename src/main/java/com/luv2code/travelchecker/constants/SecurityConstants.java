package com.luv2code.travelchecker.constants;

import com.luv2code.travelchecker.domain.enums.RoleType;

public final class SecurityConstants {

    private SecurityConstants() {
        // PMD
    }

    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 300000L; // 15 minutes 900000L
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 600000L; // 7 days 604800000L
    public static final Long PASSWORD_EXPIRATION_TIME = 3600000L; // 1 hour

    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public static final String AUTHENTICATION_URL = "/authentication";
    public static final String AUTHORIZATION_URL =  "/authorization";
    public static final String AUTH_ME_URL =  "/auth/me";
    public static final String USERS_URL = "/users/**";
    public static final String PROFILES_UPDATE_ME_URL = "/profiles/me";
    public static final String MARKERS_URL = "/markers/**";
    public static final String MAPBOX_URL = "/mapbox/**";
    public static final String FORGOT_PASSWORD_URL = "/forgot-password";
    public static final String REFRESH_TOKEN_URL = "/refresh-token";

    public static final String ADMIN_ROLE = RoleType.ADMIN.name();
    public static final String USER_ROLE = RoleType.USER.name();

}
