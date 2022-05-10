/*
 * @(#) GlobalExceptionHandler.java 2018-08-06
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.controller.handler;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Joiner;
import com.senior.common.Result;
import com.senior.common.ResultStatus;
import com.senior.common.exception.BusinessAuthenticationException;
import com.senior.common.exception.BusinessException;
import com.senior.common.exception.BusinessRuntimeException;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一异常处理
 * 
 * @author senior
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public Result<Void> authenticationHandler(AuthenticationException e) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = servletRequestAttributes.getRequest();
            log.info("authentication error: url={}", request.getRequestURI());
        }
        if (e instanceof BadCredentialsException) {
            log.warn("BadCredentialsException: {}", e.getMessage());
        } else {
            log.error("authentication exception:{}", e.getMessage(), e);
        }
        if (e instanceof BusinessAuthenticationException) {
            return Result.of(((BusinessAuthenticationException) e).getResultStatus(), null);
        }
        return Result.of(ResultStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler
    @ResponseBody
    public Result<Void> accessDeniedHandler(AccessDeniedException e) {
        log.error("access denied exception:", e);
        return Result.of(ResultStatus.FORBIDDEN, null);
    }

    /**
     * 参数不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.debug(e.getMessage());
        return Result.of(ResultStatus.ILLEGAL_ARGUMENT.getCode(),
                Joiner.on(";").skipNulls().join(e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList())),
                Joiner.on(";").skipNulls().join(e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList())));
    }

    /**
     * 参数不正确
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result bindException(BindException e) {
        log.debug(e.getMessage());
        return Result.of(ResultStatus.ILLEGAL_ARGUMENT.getCode(), ResultStatus.ILLEGAL_ARGUMENT.getMsg(),
                Joiner.on(";").skipNulls().join(e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList())));
    }

    /**
     * 统一业务错误提醒
     */
    @ExceptionHandler(BusinessRuntimeException.class)
    @ResponseBody
    public Result businessRuntimeException(BusinessRuntimeException e) {
        log.warn(e.getMessage(), e);
        return e.buildResult();
    }

    /**
     * 统一业务错误提醒
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result businessException(BusinessException e) {
        log.warn(e.getMessage(), e);
        return e.buildResult();
    }

    /**
     * 统一错误提醒
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        log.warn(e.getMessage(), e);
        return Result.error(ResultStatus.ERROR.getCode(), e.getMessage());
    }
}
