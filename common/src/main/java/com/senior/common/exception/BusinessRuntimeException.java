/*
 * @(#) BusinessRuntimeException.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.exception;

import com.senior.common.Result;
import com.senior.common.base.BaseResultStatus;

/**
 * @author senior
 */
public class BusinessRuntimeException extends RuntimeException {
    private int statusCode = BaseResultStatus.ERROR_CODE;

    public BusinessRuntimeException() {
        super(BaseResultStatus.ERROR_MESSAGE);
    }

    public BusinessRuntimeException(BaseResultStatus status) {
        super(status.getMsg());
        this.statusCode = status.getCode();
    }

    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(BaseResultStatus status, String message) {
        super(message);
        this.statusCode = status.getCode();
    }

    public BusinessRuntimeException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BusinessRuntimeException(BaseResultStatus status, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = status.getCode();
    }

    public BusinessRuntimeException(BaseResultStatus status, Throwable cause) {
        super(status.getMsg(), cause);
        this.statusCode = status.getCode();
    }

    public <T> Result<T> buildResult() {
        return Result.of(statusCode, this.getMessage(), null);
    }
}
