/*
 * @(#) AuthToken.java 2020-11-29
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import lombok.Getter;

/**
 * @author senior
 */
public class AuthToken extends AbstractAuthenticationToken {
    @Getter
    private final String passport;
    @Getter
    private final String password;

    public AuthToken(String passport, String password) {
        super(null);
        this.passport = passport;
        this.password = password;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
