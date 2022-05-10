/*
 * @(#) AccountStatus.java 2019-05-29
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.enums;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author senior
 */
@AllArgsConstructor
public enum RoleType {

    /** 自定义角色 */
    CUSTOM_ROLE(1, "自定义角色"),

    /** 系统默认角色 */
    DEFAULT_ROLE(2, "系统默认角色");

    private static final Map<Integer, RoleType> MAP = Maps.newHashMap();

    static {
        for (RoleType value : values()) {
            MAP.put(value.getCode(), value);
        }
    }

    @Getter
    private final Integer code;
    @Getter
    private final String desc;

    public static RoleType getByCode(Integer code) {
        return MAP.get(code);
    }

}
