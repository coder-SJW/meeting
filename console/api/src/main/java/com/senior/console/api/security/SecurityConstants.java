/*
 * @(#) SecurityConstants.java 2020-11-13
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.security;

/**
 * @author senior
 */
public class SecurityConstants {
    public static final String LOGIN_URL = "/api/account/login";
    public static final String LOGOUT_URL = "/api/account/logout";
    public static final String ACCOUNT_CURRENT = "/api/account/current";
    public static final String HEADER_TOKEN = "Auth-Token";

    public static final String PASSPORT_KEY = "passport";
    @SuppressWarnings("squid:S2068")
    public static final String PASSWORD_KEY = "password";

    private SecurityConstants() {
    }
}
