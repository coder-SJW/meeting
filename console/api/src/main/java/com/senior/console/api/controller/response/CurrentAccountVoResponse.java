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
     * ๆ้ๅ่กจ
     */
    private List<String> authorities;
}
