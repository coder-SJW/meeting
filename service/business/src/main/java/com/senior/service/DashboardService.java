/*
 * @(#) DashboardService.java 2020-12-13
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.service;

import java.util.List;

/**
 * @author senior
 */
public interface DashboardService {

    /**
     * 借阅次数最多的图书分类排行榜
     * 
     * @return
     */
    List<Object> popularBookClassTop(Object request);

    /**
     * 每日借阅图书分类榜
     *
     * @param request
     * @return
     */
    List<Object> dailyPopularBookClass(Object request);

    /**
     * 查询借阅记录数量
     *
     * @param request
     * @return
     */
    List<Object> queryBorrowCount(Object request);

    /**
     * 查询还书记录数量
     *
     * @param request
     * @return
     */
    List<Object> queryBackCount(Object request);

}
