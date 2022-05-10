/*
 * @(#) Result.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common;

import com.senior.common.base.BaseResultStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Slf4j
@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Result<T> {
    /**
     * 返回结果状态码
     */
    @NonNull
    private int code;

    /**
     * 返回结果状态信息
     */
    private String msg;

    /**
     * 返回主体数据
     */
    private T data;

    /**
     * 构造通用成功返回{@link Result Result}工厂方法
     *
     * @return {@link Result Result}
     */
    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * 构造通用成功返回{@link Result Result}工厂方法
     *
     * @param data 返回数据
     * @return {@link Result Result}
     */
    public static <T> Result<T> ok(T data) {
        return Result.of(BaseResultStatus.SUCCESS_CODE, BaseResultStatus.SUCCESS_MESSAGE, data);
    }

    /**
     * 构造通用失败返回{@link Result Result}工厂方法
     *
     * @return {@link Result Result}
     */
    public static <T> Result<T> error() {
        return Result.error(null);
    }

    /**
     * 构造通用失败返回{@link Result Result}工厂方法
     *
     * @param msg 返回消息
     * @return {@link Result Result}
     */
    public static <T> Result<T> error(String msg) {
        return Result.of(BaseResultStatus.ERROR_CODE, msg);
    }

    /**
     * 构造通用失败返回{@link Result Result}工厂方法
     *
     * @param msg 返回消息
     * @return {@link Result Result}
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return Result.of(code, msg);
    }

    /**
     * 构造{@link Result Result}工厂方法
     *
     * @param code 自定义返回码
     * @param msg 自定义返回消息
     * @return {@link Result Result}
     */
    public static <T> Result<T> of(Integer code, String msg) {
        return Result.<T>builder().code(code).msg(msg).data(null).build();
    }

    /**
     * 构造{@link Result Result}工厂方法
     *
     * @param code 自定义返回码
     * @param msg 自定义返回消息
     * @param data 返回数据
     * @return {@link Result Result}
     */
    public static <T> Result<T> of(Integer code, String msg, T data) {
        return Result.<T>builder().code(code).msg(msg).data(data).build();
    }

    /**
     * 构造{@link Result Result}工厂方法
     *
     * @param status 自定义返回
     * @param data 返回数据
     * @return {@link Result Result}
     */
    public static <T> Result<T> of(BaseResultStatus status, T data) {
        return Result.<T>builder().code(status.getCode()).msg(status.getMsg()).data(data).build();
    }
}
