package com.senior.console.api.controller.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.senior.common.base.BaseVoResponse;
import com.senior.domain.bo.response.ImageBo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会议室(Room) QueryVoResponse
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomQueryVoResponse implements BaseVoResponse {

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 会议室
     */
    @ExcelProperty(value = "会议室")
    private String name;

    /**
     * 实景照片
     */
    @ExcelIgnore
    private List<ImageBo> imageList = new ArrayList<>();
    @ExcelIgnore
    private List<Long> roomImageIds = new ArrayList<>();

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String description;

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

    public Date getCreateTimeDate() {
        return Optional.ofNullable(createTime).map(Date::new).orElse(null);
    }

    public Date getUpdateTimeDate() {
        return Optional.ofNullable(updateTime).map(Date::new).orElse(null);
    }

    /**
     * 会议室类型
     */
    @ExcelIgnore
    private String type;

}
