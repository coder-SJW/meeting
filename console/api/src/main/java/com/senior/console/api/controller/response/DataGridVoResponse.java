/*
 * @(#) CurrentGridVoResponse.java 2020-12-19
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.controller.response;

import com.github.abel533.echarts.Option;
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
public class DataGridVoResponse implements BaseVoResponse {
    private Option hourlyOption;
    private Option dailyOption;
    private Option weeklyOption;
    private Option monthlyOption;
}
