/*
 * @(#) UrsLogoutSuccessHandler.java 2016年8月31日
 * 
 * Copyright 2020 senior. All rights reserved.
 */
package com.senior.console.api.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.senior.common.Result;
import com.senior.domain.bo.response.AccountQueryBoResponse;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author senior
 */
@Slf4j
@Service
public class AccountLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        try {
            if (authentication != null) {
                AccountUserDetails details = (AccountUserDetails) authentication.getPrincipal();
                AccountQueryBoResponse account = details.getAccount();
                log.info("user logout ,account:{}", details.getUsername());
            }
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().print(JSON.toJSONString(Result.ok()));
        } catch (Exception e) {
            log.error("failed to logout", e);
        }
    }

}
