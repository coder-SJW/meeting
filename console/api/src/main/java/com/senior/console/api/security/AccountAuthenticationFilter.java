/*
 * @(#) AuthenticationTokenFilter.java 2020-11-13
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Slf4j
public class AccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Resource
    private AccountAuthenticationSuccessHandler accountAuthenticationSuccessHandler;

    public AccountAuthenticationFilter() {
        super(SecurityConstants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String passport = StringUtils.defaultString(request.getParameter(SecurityConstants.PASSPORT_KEY));
        String password = StringUtils.defaultString(request.getParameter(SecurityConstants.PASSWORD_KEY));
        AuthToken authToken = new AuthToken(passport, password);
        Authentication authenticate = this.getAuthenticationManager().authenticate(authToken);
        Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();
        accountAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authenticate, oldAuthentication);
        return authenticate;
    }
}
