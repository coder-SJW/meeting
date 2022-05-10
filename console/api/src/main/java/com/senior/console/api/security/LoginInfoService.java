/*
 * @(#) ControllerService.java 2020-11-30
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.security;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.senior.common.kits.CollectionKits;
import com.senior.domain.bo.response.AccountQueryBoResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Slf4j
@Service
public class LoginInfoService {

    /**
     * 获取当前登录账号信息
     * 
     * @return
     */
    public AccountQueryBoResponse getLoginAccount() {
        AccountUserDetails userDetails = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getAccount();
    }

    /**
     * 获取当前登录账号拥有的权限
     * 
     * @return
     */
    public List<String> grantedAuthorities() {
        AccountUserDetails details = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<AuthGrantedAuthority> grantedAuthorities = details.getAuthorities();
        return Collections.unmodifiableList(CollectionKits.nullToEmpty(grantedAuthorities).stream()
                .map(AuthGrantedAuthority::getAuthority).collect(Collectors.toList()));
    }
}
