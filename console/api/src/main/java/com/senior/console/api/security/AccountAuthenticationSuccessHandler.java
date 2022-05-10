/*
 * @(#) YunAccountAuthenticationSuccessHandler.java Jun 8, 2011
 *
 * Copyright 2020 senior. All rights reserved.
 */
package com.senior.console.api.security;

import java.time.Duration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.senior.domain.bo.request.AccountUpdateBoRequest;
import com.senior.domain.model.AccountDo;
import com.senior.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Slf4j
@Service
public class AccountAuthenticationSuccessHandler {

    private static final Duration REMEMBER_ME_SESSION_TIMEOUT = Duration.ofDays(15);
    private static final Duration DEFAULT_SESSION_TIMEOUT = Duration.ofMinutes(30);
    private static final Duration MAIN_SESSION_TIMEOUT = Duration.ofDays(1);

    @Resource
    private AccountService accountService;

    /**
     * 因为需要oldAuthentication，没办法放到 spring 的 success handler 中。之所以需要old authentication，是因为前端每次加载页面都会调用登录接口。
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication, Authentication oldAuthentication) {
        HttpSession session = request.getSession();
        AccountUserDetails userDetails = (AccountUserDetails) authentication.getPrincipal();
        AccountDo account = userDetails.getAccount();

        // 判断登录账号有没有变化
        // 此次认证之前，session可能存在的认证信息
        boolean hasSessionAuth = oldAuthentication != null && oldAuthentication.isAuthenticated();
        String sessionPassport = null;
        if (hasSessionAuth) {
            sessionPassport = ((AccountUserDetails) oldAuthentication.getPrincipal()).getAccount().getPassport();
        }
        // 登录账号变化了
        if (sessionPassport == null || !sessionPassport.equals(account.getPassport())) {
            boolean rememberMe = Boolean.TRUE.toString().equalsIgnoreCase(request.getParameter("rememberMe"));

            log.info("登录成功, id={},passport={},sessionId={}", account.getId(), account.getPassport(),
                    session.getId());

            setSessionTimeout(session, rememberMe);
        }
        updateLoginTime(account);
    }

    /**
     * 登录成功后修改最近一次的登录时间
     * 
     * @param account
     */
    private void updateLoginTime(AccountDo account) {
        AccountUpdateBoRequest accountBo = new AccountUpdateBoRequest();
        accountBo.setId(account.getId());
        accountBo.setLastLoginTime(System.currentTimeMillis());
        accountService.updateById(accountBo);
    }

    private void setSessionTimeout(HttpSession session, boolean rememberMe) {
        if (rememberMe) {
            session.setMaxInactiveInterval((int) REMEMBER_ME_SESSION_TIMEOUT.getSeconds());
        } else {
            session.setMaxInactiveInterval((int) DEFAULT_SESSION_TIMEOUT.getSeconds());
        }
    }
}
