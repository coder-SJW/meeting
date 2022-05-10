/*
 * @(#) ChangePasswordVoRequest.java 2020-11-29
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.senior.common.base.BaseVoRequest;

import lombok.Data;

/**
 * @author senior
 */
@Data
public class ChangePasswordVoRequest implements BaseVoRequest {

    /**
     * 旧密码
     */
    @NotNull
    @Size(min = 6, max = 16, message = "密码非法，长度为:[6,16]")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotNull
    @Size(min = 6, max = 16, message = "密码非法，长度为:[6,16]")
    private String newPassword;
}
