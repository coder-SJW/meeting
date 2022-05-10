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
public class CurrentGridVoResponse implements BaseVoResponse {
    /**
     * 今日访客量
     */
    private Long todayVisitors;
    /**
     * 现有图书库存量
     */
    private Long totalStock;
    /**
     * 待还图书总量
     */
    private Long notReturnedQuantity;
}
