/*
 * @(#) CurrentGridVoResponse.java 2020-12-19
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.controller.response;

import com.senior.common.base.BaseVoResponse;

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
public class AccountGridVoResponse implements BaseVoResponse {
    /**
     * 今日访客量
     */
    private Long todayVisitors;
    /**
     * 总用户数
     */
    private Long totalAccount;
    /**
     * 最近一周新增新增用户数
     */
    private Long newAccountInLastWeek;
}
