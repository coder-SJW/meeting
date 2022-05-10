/*
 * @(#) AccountStatus.java 2019-05-29
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.enums;

import lombok.AllArgsConstructor;

/**
 * @author senior
 */
@AllArgsConstructor
public enum GroupType {

    /** 按月份聚合 */
    MONTH,

    /** 按天聚合 */
    DAY,

    /** 按小时聚合 */
    HOURS;

}
