/*
 * @(#) BusinessAuthenticationException.java 2020-01-13
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.exception;

import org.springframework.security.core.AuthenticationException;

import com.senior.common.ResultStatus;

/**
 * @author senior
 */
@SuppressWarnings("squid:S2166")
public class BusinessAuthenticationException extends AuthenticationException {
    private static final long serialVersionUID = -3838258166017523782L;
    private final ResultStatus resultStatus;

    public BusinessAuthenticationException(ResultStatus resultStatus) {
        super(resultStatus.getMsg());
        this.resultStatus = resultStatus;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }
}
