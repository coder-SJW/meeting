/*
 * @(#) AccountSessionServiceImpl.java 2020-11-18
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.security;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.senior.common.ResultStatus;
import com.senior.common.enums.AccountStatus;
import com.senior.common.exception.BusinessAuthenticationException;
import com.senior.common.kits.PreconditionsKits;
import com.senior.domain.bo.response.AccountQueryBoResponse;
import com.senior.domain.bo.response.RoleQueryBoResponse;
import com.senior.service.AccountService;
import com.senior.service.RoleService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Slf4j
@Service
public class AccountUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private AccountService accountService;
    @Resource
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return loadByPassport(s);
    }

    public AccountUserDetails loadByPassport(String passport) {
        PreconditionsKits.checkNotBlank(passport, "passport is blank");
        AccountQueryBoResponse account = accountService.getByPassport(passport);
        if (account == null) {
            log.error("账号不存在: passport={}", passport);
            throw new BusinessAuthenticationException(ResultStatus.WRONG_PASSWORD);
        }
        return loadCommon(account);
    }

    private AccountUserDetails loadCommon(AccountQueryBoResponse account) {
        if (account.getStatus() != AccountStatus.ENABLED.getCode()) {
            log.error("账号已禁用: passport={}", account.getPassport());
            throw new BusinessAuthenticationException(ResultStatus.ACCOUNT_DISABLED);
        }
        Long roleId = account.getRoleId();
        if (roleId == null || roleId == 0) {
            log.error("账号无角色: passport={}", account.getPassport());
            throw new BusinessAuthenticationException(ResultStatus.FORBIDDEN);
        }
        RoleQueryBoResponse role = roleService.getById(roleId);
        List<AuthGrantedAuthority> grantedAuthorities = role.getAuthorities().stream().map(AuthGrantedAuthority::new)
                .collect(Collectors.toList());
        return AccountUserDetails.builder()
                .account(account)
                .grantedAuthorities(Collections.unmodifiableList(grantedAuthorities)).build();

    }
}
