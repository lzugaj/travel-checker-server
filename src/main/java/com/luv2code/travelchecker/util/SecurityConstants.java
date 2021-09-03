package com.luv2code.travelchecker.util;

import com.luv2code.travelchecker.domain.enums.RoleType;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds

    public static final String AUTHENTICATION_URL = "/authentication";
    public static final String AUTHORIZATION_URL =  "/authorization";
    public static final String USER_FIND_BY_ID_URL = "/users/*";
    public static final String USER_FIND_ALL_URL = "/users";
    public static final String USER_UPDATE_URL = "/users";
    public static final String MARKER_URL = "/markers/**";
    public static final String MAPBOX_URL = "/mapbox/**";

    public static final String ADMIN_ROLE = RoleType.ADMIN.name();
    public static final String USER_ROLE = RoleType.USER.name();

}
