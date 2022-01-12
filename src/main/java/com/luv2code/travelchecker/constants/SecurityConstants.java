package com.luv2code.travelchecker.constants;

import com.luv2code.travelchecker.domain.enums.RoleType;

public class SecurityConstants {

    public static final Long EXPIRATION_TIME = 31556952000L;
    public static final Long PASSWORD_EXPIRATION_TIME = 86400000L;

    public static final String AUTHENTICATION_URL = "/authentication";
    public static final String AUTHORIZATION_URL =  "/authorization";
    public static final String AUTH_ME_URL =  "/auth/me";
    public static final String USERS_URL = "/users/**";
    public static final String PROFILES_UPDATE_ME_URL = "/profiles/me";
    public static final String MARKERS_URL = "/markers/**";
    public static final String MAPBOX_URL = "/mapbox/**";
    public static final String FORGOT_PASSWORD_URL = "/forgot-password";
    // public static final String RESET_PASSWORD_URL = "/reset-password**";

    public static final String ADMIN_ROLE = RoleType.ADMIN.name();
    public static final String USER_ROLE = RoleType.USER.name();

}
