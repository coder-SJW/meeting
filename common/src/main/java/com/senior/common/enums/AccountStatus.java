/*
 * @(#) AccountStatus.java 2019-05-29
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author senior
 */
@AllArgsConstructor
public enum AccountStatus {

    /** 启用状态 */
    ENABLED(0),

    /** 禁用状态 */
    DISABLED(1),;

    @Getter
    private Integer code;

}
