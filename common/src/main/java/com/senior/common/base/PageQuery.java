/*
 * @(#) PageQuery.java 2020-11-15
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.base;

/**
 * 分页查询通用接口 <br/>
 * <p>
 * 全局约定！pageNum：当前页码；pageSize：每页展示的数据条数
 *
 * @author senior
 */
public interface PageQuery {
    /**
     * 获取每页大小
     *
     * @return 每页大小
     */
    Integer getPageSize();

    /**
     * 获取第几页，从 1 开始
     *
     * @return 第几页
     */
    Integer getPageNum();

    /**
     * 指定页数据库记录偏移量
     *
     * @return 数据库记录偏移量
     */
    default Integer getOffset() {
        if (getPageNum() == null || getPageSize() == null) {
            return null;
        }
        return getPageSize() * (getPageNum() - 1);
    }
}
