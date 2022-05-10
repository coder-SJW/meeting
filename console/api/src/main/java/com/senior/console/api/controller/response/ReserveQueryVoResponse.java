package com.senior.console.api.controller.response;

import java.util.Date;
import java.util.Optional;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.senior.common.base.BaseVoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预约记录(Reserve) QueryVoResponse
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveQueryVoResponse implements BaseVoResponse {

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 会议室
     */
    @ExcelIgnore
    private Long roomId;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 预约人
     */
    @ExcelIgnore
    private Long accountId;

    /**
     * 开始时间
     */
    @ExcelIgnore
    private Long reserveStartTime;
    @ExcelProperty(value = "开始时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date reserveStartTimeDate;
    /**
     * 结束时间
     */
    @ExcelIgnore
    private Long reserveEndTime;
    @ExcelProperty(value = "结束时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date reserveEndTimeDate;
    /**
     * 创建时间
     */
    @ExcelIgnore
    private Long createTime;
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeDate;
    /**
     * 修改时间
     */
    @ExcelIgnore
    private Long updateTime;
    @ExcelProperty(value = "修改时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimeDate;
    /**
     * 会议室
     */
    @ExcelProperty(value = "会议室")
    private String roomName;
    /**
     * 预约人
     */
    @ExcelProperty(value = "预约人")
    private String accountPassport;

    public Date getReserveStartTimeDate() {
        return Optional.ofNullable(reserveStartTime).map(Date::new).orElse(null);
    }

    public Date getReserveEndTimeDate() {
        return Optional.ofNullable(reserveEndTime).map(Date::new).orElse(null);
    }

    public Date getCreateTimeDate() {
        return Optional.ofNullable(createTime).map(Date::new).orElse(null);
    }

    public Date getUpdateTimeDate() {
        return Optional.ofNullable(updateTime).map(Date::new).orElse(null);
    }

}
