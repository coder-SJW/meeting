/*
 * @(#) YunAccountAuthenticationFailureHandler.java Jun 10, 2011
 * 
 * Copyright 2020 senior. All rights reserved.
 */
package com.senior.console.api.security;

import java.util.Date;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Slf4j
@Service
public class AccountAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) {
        log.info("用户登录失败 :" + exception.getMessage());
        logRequest(log, request);
        resolver.resolveException(request, response, null, exception);
    }

    private void logRequest(Logger log, HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info("Request method={}, consoleUrl={}, qs={}, sessionId={}, sessionCreateTime={}", request.getMethod(),
                request.getRequestURI(), request.getQueryString(), session.getId(),
                new Date(session.getCreationTime()));
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String value = headers.nextElement();
                log.info("Header {}:{}", headerName, value);
            }
        }
    }
}
