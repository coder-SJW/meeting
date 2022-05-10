/*
 * @(#) ResultStatus.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common;

import com.senior.common.base.BaseResultStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定义后端异常code，通用异常使用三位code码，业务异常使用六位
 *
 * @author senior
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public enum ResultStatus implements BaseResultStatus {
    /**
     * 200 成功 使用约定的成功状态码
     */
    SUCCESS(BaseResultStatus.SUCCESS_CODE, BaseResultStatus.SUCCESS_MESSAGE),

    /**
     * 500 异常 使用约定的失败状态码
     */
    ERROR(BaseResultStatus.ERROR_CODE, BaseResultStatus.ERROR_MESSAGE),

    /**
     * 参数错误
     */
    ILLEGAL_ARGUMENT(400, "参数错误"),

    UNAUTHORIZED(401, "未认证"),

    FORBIDDEN(403, "未授权"),

    WRONG_PASSWORD(411, "账号或密码错误"),

    ACCOUNT_DISABLED(412, "账号已被禁用"),
    ;

    /**
     * 返回状态码,{@link ResultStatus#SUCCESS_CODE SUCCESS(200)}表示成功
     */
    private Integer code;

    /**
     * 返回描述信息
     */
    private String msg;
}
