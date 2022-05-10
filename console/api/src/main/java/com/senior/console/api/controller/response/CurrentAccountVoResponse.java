/*
 * @(#) CurrentAccountVoResponse.java 2020-11-30
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.controller.response;

import java.util.List;

import lombok.Data;

/**
 * @author senior
 */
@Data
public class CurrentAccountVoResponse extends AccountQueryVoResponse {
    /**
     * 权限列表
     */
    private List<String> authorities;
}
