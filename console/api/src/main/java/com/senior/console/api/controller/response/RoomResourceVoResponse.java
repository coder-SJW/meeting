/*
 * @(#) RoomResourceVoResponse.java 2021-09-09
 *
 * Copyright 2021 NetEase.com, Inc. All rights reserved.
 */

package com.senior.console.api.controller.response;

import java.util.List;

import com.senior.common.base.BaseVoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangwu
 * @version 2021-09-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResourceVoResponse implements BaseVoResponse {

    private Long id;
    private String title;
    private BusinessHours businessHours;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessHours {
        private String startTime;
        private String endTime;
        private List<Integer> daysOfWeek;
    }
}
