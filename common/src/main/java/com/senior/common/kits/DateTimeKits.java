/*
 * @(#) DateTimeKits.java 2020-12-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.kits;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Slf4j
public class DateTimeKits {

    /**
     * 日期格式化
     * 
     * @param time
     * @param format
     * @return
     */
    public static String format(long time, String format) {
        if (time < 0L) {
            return "N/A";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date(time));
        }
    }

    /**
     * 解析日期字符串
     *
     * @param timeStr
     * @param format
     * @return
     */
    public static Long parse(String timeStr, String format) {
        PreconditionsKits.checkNotBlank(timeStr, "timeStr cannot be blank");
        PreconditionsKits.checkNotBlank(format, "format cannot be blank");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(timeStr).getTime();
        } catch (ParseException e) {
            log.error("Parse timeStr error, timeStr={}, format={}", timeStr, format, e);
            return 0L;
        }
    }

    /**
     * 获取给定时间当天的开始时间
     * 
     * @param time
     * @return
     */
    public static long getStartTimeOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取给定时间当天的结束时间
     * 
     * @param time
     * @return
     */
    public static long getEndTimeOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取给定时间当月的开始时间
     * 
     * @param time
     * @return
     */
    public static long getStartTimeOfMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取给定时间当月的结束时间
     * 
     * @param time
     * @return
     */
    public static long getEndTimeOfMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(5, calendar.getActualMaximum(5));
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 999);
        return calendar.getTimeInMillis();
    }

}
