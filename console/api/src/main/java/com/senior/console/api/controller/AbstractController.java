package com.senior.console.api.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.senior.common.kits.DateTimeKits;

/**
 * @author senior
 */
public class AbstractController {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final Map<String, String> TIME_MAP = new LinkedHashMap<>();
    public static final List<String> HOUR_GROUP = Lists.newArrayList("1-4点", "5-8点", "9-12点", "13-16点", "17-20点",
            "21-24点");

    static {
        TIME_MAP.put("01", "1-4点");
        TIME_MAP.put("02", "1-4点");
        TIME_MAP.put("03", "1-4点");
        TIME_MAP.put("04", "1-4点");
        TIME_MAP.put("05", "5-8点");
        TIME_MAP.put("06", "5-8点");
        TIME_MAP.put("07", "5-8点");
        TIME_MAP.put("08", "5-8点");
        TIME_MAP.put("09", "9-12点");
        TIME_MAP.put("10", "9-12点");
        TIME_MAP.put("11", "9-12点");
        TIME_MAP.put("12", "9-12点");
        TIME_MAP.put("13", "13-16点");
        TIME_MAP.put("14", "13-16点");
        TIME_MAP.put("15", "13-16点");
        TIME_MAP.put("16", "13-16点");
        TIME_MAP.put("17", "17-20点");
        TIME_MAP.put("18", "17-20点");
        TIME_MAP.put("19", "17-20点");
        TIME_MAP.put("20", "17-20点");
        TIME_MAP.put("21", "21-24点");
        TIME_MAP.put("22", "21-24点");
        TIME_MAP.put("23", "21-24点");
        TIME_MAP.put("00", "21-24点");
    }

    /**
     * 获取指定月份集合
     *
     * @param intervalToCurrent
     * @return
     */
    public static List<String> getMonths(int intervalToCurrent) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        List<String> months = Lists.newArrayList();
        for (int i = 0; i < intervalToCurrent; i++) {
            months.add(DateTimeKits.format(c.getTime().getTime(), YYYY_MM));
            c.add(Calendar.MONTH, -1);
        }
        Collections.reverse(months);
        return months;
    }

    /**
     * 获取指定日期集合
     *
     * @param intervalToCurrent
     * @return
     */
    public static List<String> getDays(int intervalToCurrent) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        List<String> days = Lists.newArrayList();
        for (int i = 0; i < intervalToCurrent; i++) {
            days.add(DateTimeKits.format(c.getTime().getTime(), YYYY_MM_DD));
            c.add(Calendar.DAY_OF_YEAR, -1);
        }
        Collections.reverse(days);
        return days;
    }

    public static String getDayOfWeek(String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(new Date(DateTimeKits.parse(day, YYYY_MM_DD)));
    }

    /**
     * 导出Excel时设置response的header等必要信息ß
     *
     * @param servletResponse
     * @param fileName
     */
    protected void setExcelResponse(HttpServletResponse servletResponse, String fileName) {
        servletResponse.setContentType("application/vnd.ms-excel");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }

    /**
     * 获取指定月份的开始时间
     *
     * @param intervalToCurrent
     * @return
     */
    public long getTargetMonthBeginTime(int intervalToCurrent) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -intervalToCurrent);
        return DateTimeKits.getStartTimeOfMonth(c.getTimeInMillis());
    }

    /**
     * 获取指定日期的开始时间
     *
     * @param intervalToCurrent
     * @return
     */
    public long getTargetDayBeginTime(int intervalToCurrent) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, -intervalToCurrent);
        return DateTimeKits.getStartTimeOfDay(c.getTimeInMillis());
    }

    /**
     * 获取指定小时集合
     * 
     * @param intervalToCurrent
     * @return
     */
    public List<String> getHours(int intervalToCurrent) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        List<String> days = Lists.newArrayList();
        for (int i = 0; i < intervalToCurrent; i++) {
            days.add(DateTimeKits.format(c.getTime().getTime(), "HH"));
            c.add(Calendar.HOUR, -1);
        }
        Collections.reverse(days);
        return days;
    }
}
