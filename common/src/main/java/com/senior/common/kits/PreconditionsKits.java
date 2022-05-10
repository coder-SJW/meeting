/*
 * @(#) PreconditionsKits.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.kits;

import java.util.Collection;

import com.senior.common.ResultStatus;
import com.senior.common.exception.BusinessRuntimeException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * @author senior
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PreconditionsKits {

    /**
     * 断言目标对象不是 null，反之则抛出参数错误运行期异常 #{@link BusinessRuntimeException}
     *
     * @param reference 目标对象
     * @param format format string
     * @param args 参数
     * @param <T>
     */
    public static <T> void checkNotNull(T reference, String format, Object... args) {
        if (reference == null) {
            String msg = (null != args && args.length > 0) ? String.format(format, args) : format;
            throw new BusinessRuntimeException(ResultStatus.ILLEGAL_ARGUMENT, msg);
        }
    }

    /**
     * 断言目标字符串不是 null 也不是空，反之则抛出参数错误运行期异常 #{@link BusinessRuntimeException}
     *
     * @param reference 目标字符串
     * @param format format string
     * @param args 参数
     */
    public static void checkNotBlank(String reference, String format, Object... args) {
        if (reference == null || reference.trim().isEmpty()) {
            String msg = (null != args && args.length > 0) ? String.format(format, args) : format;
            throw new BusinessRuntimeException(ResultStatus.ILLEGAL_ARGUMENT, msg);
        }
    }

    /**
     * 断言目标集合不是 null 也不为空，反之则抛出参数错误运行期异常 #{@link BusinessRuntimeException}
     *
     * @param collection 目标集合
     * @param format format string
     * @param args 参数
     */
    public static void checkNotEmpty(Collection<?> collection, String format, Object... args) {
        if (collection == null || collection.isEmpty()) {
            String msg = (null != args && args.length > 0) ? String.format(format, args) : format;
            throw new BusinessRuntimeException(ResultStatus.ILLEGAL_ARGUMENT, msg);
        }
    }

    /**
     * 断言目标布尔值为 true，反之则抛出参数错误运行期异常 #{@link BusinessRuntimeException}
     *
     * @param expression 目标布尔值
     * @param format format string
     * @param args 参数
     */
    public static void checkArgs(boolean expression, String format, Object... args) {
        if (!expression) {
            String msg = (null != args && args.length > 0) ? String.format(format, args) : format;
            throw new BusinessRuntimeException(ResultStatus.ILLEGAL_ARGUMENT, msg);
        }
    }

}
