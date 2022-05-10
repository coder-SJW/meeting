/*
 * @(#) ReserveEventVoResponse.java 2021-09-09
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
 * @author sjw
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveEventVoResponse implements BaseVoResponse {

    private String title;
    private String start;
    private String end;
    private List<Long> resourceIds;
    private String color;
    private String textColor;
    private Boolean editable;
    private Boolean overlap;
    private Boolean displayEventTime;
    private Integer eventMaxStack;
}
