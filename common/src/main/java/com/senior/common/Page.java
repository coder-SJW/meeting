/*
 * @(#) Page.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用分页结果返回 需配合{@link Result Result}使用
 * <p>
 * 例如:
 *
 * <pre>
 *     Result.ok(Page.empty())
 *
 *     or
 *
 *     Result.ok(Page.fill(0,Lists.newArrayList()))
 * </pre>
 *
 * @author senior
 * @see Result
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Page<T> implements Serializable {
    /**
     * 总条数
     */
    private long count;

    /**
     * 当前页数据
     */
    private List<T> rows;

    /**
     * 构建分页数据
     *
     * @param count 总条数
     * @param rows 当前页数据
     * @return {@link Page Page}
     */
    public static <T> Page<T> fill(long count, List<T> rows) {
        return Page.<T>builder().count(count).rows(rows).build();
    }

    /**
     * 构建空分页
     *
     * @return {@link Page Page}
     */
    public static <T> Page<T> empty() {
        return Page.fill(0, Lists.newArrayList());
    }
}
