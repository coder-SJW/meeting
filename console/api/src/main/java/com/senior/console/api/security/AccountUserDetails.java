/*
 * @(#) UserDetails.java 2020-11-18
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.security;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.senior.common.enums.AccountStatus;
import com.senior.domain.bo.response.AccountQueryBoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author senior
 */
@AllArgsConstructor
@Builder
public class AccountUserDetails implements UserDetails {
    private final List<AuthGrantedAuthority> grantedAuthorities;
    /**
     * 账号信息
     */
    @Getter
    private AccountQueryBoResponse account;

    @Override
    public List<AuthGrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(this.grantedAuthorities);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return account.getPassport();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return account.getStatus() == AccountStatus.ENABLED.getCode();
    }
}
