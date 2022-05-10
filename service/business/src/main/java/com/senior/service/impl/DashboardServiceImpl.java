/*
 * @(#) DashboardServiceImpl.java 2020-12-13
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senior.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    @Override
    public List<Object> popularBookClassTop(Object request) {
        return null;
    }

    @Override
    public List<Object> dailyPopularBookClass(Object request) {
        return null;
    }

    @Override
    public List<Object> queryBorrowCount(Object request) {
        return null;
    }

    @Override
    public List<Object> queryBackCount(Object request) {
        return null;
    }
}
