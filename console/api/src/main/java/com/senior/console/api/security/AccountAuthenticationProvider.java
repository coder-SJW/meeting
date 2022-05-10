/*
 * @(#) YunAccountAuthenticationProvider.java 2019-05-28
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.security;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senior.common.ResultStatus;
import com.senior.common.exception.BusinessAuthenticationException;
import com.senior.domain.model.AccountDo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Service
@Slf4j
public class AccountAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private AccountUserDetailsServiceImpl accountUserDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        AuthToken authToken = (AuthToken) authentication;
        String passport = authToken.getPassport();
        String password = authToken.getPassword();
        if (StringUtils.isBlank(passport) || StringUtils.isBlank(password)) {
            return null;
        }
        AccountUserDetails userDetails = this.accountUserDetailsService.loadByPassport(passport);
        AccountDo account = userDetails.getAccount();
        String dbPassword = account.getPassword();
        if (!passwordEncoder.matches(password, dbPassword)) {
            throw new BusinessAuthenticationException(ResultStatus.WRONG_PASSWORD);
        }
        return createSuccessAuthentication(userDetails, authentication);
    }

    private Authentication createSuccessAuthentication(UserDetails principal, Authentication authentication) {
        // 按照 Authentication 的 javadoc，UserDetails 应该是 principal，而不是 details
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal, null,
                principal.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthToken.class.isAssignableFrom(authentication);
    }
}
