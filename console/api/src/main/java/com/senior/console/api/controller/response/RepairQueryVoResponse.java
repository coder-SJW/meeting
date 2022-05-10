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
 * 报修(Repair) QueryVoResponse
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepairQueryVoResponse implements BaseVoResponse {

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 状态;1:未处理 2:处理中 3: 已完成
     */
    @ExcelIgnore
    private Integer status;
    @ExcelProperty(value = "状态")
    private String statusMsg;
    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;
    /**
     * 会议室
     */
    @ExcelIgnore
    private Long roomId;
    /**
     * 报修人
     */
    @ExcelIgnore
    private Long accountId;
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
     * 报修人
     */
    @ExcelProperty(value = "报修人")
    private String accountPassport;

    public String getStatusMsg() {
        String msg = "";
        switch (status) {
            case 1:
                msg = "未处理";
                break;
            case 2:
                msg = "处理中";
                break;
            case 3:
                msg = "已完成";
                break;
            default:
                break;
        }
        return msg;
    }

    public Date getCreateTimeDate() {
        return Optional.ofNullable(createTime).map(Date::new).orElse(null);
    }

    public Date getUpdateTimeDate() {
        return Optional.ofNullable(updateTime).map(Date::new).orElse(null);
    }

}
