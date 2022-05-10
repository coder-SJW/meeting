/*
 * @(#) BusinessException.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.exception;

import com.senior.common.Result;
import com.senior.common.base.BaseResultStatus;

/**
 * @author senior
 */
public class BusinessException extends Exception {
    private int statusCode = BaseResultStatus.ERROR_CODE;

    public BusinessException() {
        super(BaseResultStatus.ERROR_MESSAGE);
    }

    public BusinessException(BaseResultStatus status) {
        super(status.getMsg());
        this.statusCode = status.getCode();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(BaseResultStatus status, String message) {
        super(message);
        this.statusCode = status.getCode();
    }

    public BusinessException(BaseResultStatus status, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = status.getCode();
    }

    public BusinessException(BaseResultStatus status, Throwable cause) {
        super(status.getMsg(), cause);
        this.statusCode = status.getCode();
    }

    public <T> Result<T> buildResult() {
        return Result.of(statusCode, this.getMessage(), null);
    }
}
