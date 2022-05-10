/*
 * @(#) DashboardVoResponse.java 2020-12-13
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.controller.response;

import java.util.List;
import java.util.TreeSet;

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
public class DashboardVoResponse implements BaseVoResponse {

    /**
     * 历史借阅次数最多的图书分类排行榜前三甲
     */
    private List<StockItem> popularBookClassTop;

    /**
     * 最近一周借阅次数最多的图书分类排行榜前三甲
     */
    private Options lastWeekPopularOptions;

    /**
     * 最近半年图书借阅归还趋势图
     */
    private Options lastSixMonthsOptions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StockItem {
        /**
         * 图书分类名称
         */
        private String name;
        /**
         * 借阅次数占比
         */
        private Float percent;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Options {
        private String type;
        private Title title;
        private Float xRorate;
        private TreeSet<String> labels;
        private List<Dataset> datasets;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Title {
        private String text;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Dataset {
        private String label;
        private List<Long> data;
    }
}
