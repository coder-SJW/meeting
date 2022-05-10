/*
 * @(#) ResultStatus.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.base;

/**
 * @author senior
 */
public interface BaseResultStatus {
    int SUCCESS_CODE = 200;
    String SUCCESS_MESSAGE = "成功";

    int ERROR_CODE = 500;
    String ERROR_MESSAGE = "失败";

    /**
     * 获取状态码
     *
     * @return
     */
    Integer getCode();

    /**
     * 获取返回信息
     *
     * @return
     */
    String getMsg();
}
