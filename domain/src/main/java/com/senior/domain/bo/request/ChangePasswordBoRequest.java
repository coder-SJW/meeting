/*
 * @(#) ChangePasswordBoRequest.java 2020-11-30
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.domain.bo.request;

import com.senior.common.base.BaseBoRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author senior
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordBoRequest implements BaseBoRequest {
    /**
     * 账号id
     */
    private Long id;
    /**
     * 新密码
     */
    private String password;
    /**
     * 修改时间
     */
    private Long updateTime;
}
