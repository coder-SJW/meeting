/*
 * @(#) AuthGrantedAuthority.java 2019-06-03
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.security;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

import com.google.common.base.Preconditions;

/**
 * @author senior
 */
public class AuthGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 5856769474002661748L;
    private final String authority;

    public AuthGrantedAuthority(String authority) {
        Preconditions.checkNotNull(authority);
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthGrantedAuthority that = (AuthGrantedAuthority) o;
        return Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authority);
    }

    @Override
    public String toString() {
        return authority;
    }
}
