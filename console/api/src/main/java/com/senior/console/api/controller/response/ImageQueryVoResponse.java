package com.senior.console.api.controller.response;

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
 * 图片(Image) QueryVoResponse
 *
 * @author senior
 * @since 2021-08-16 22:13:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageQueryVoResponse implements BaseVoResponse {

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 图片base64
     */
    @ExcelProperty(value = "图片base64")
    private String data;
    private List<ImageBo> images;

    /**
     * 图片名称
     */
    @ExcelProperty(value = "图片名称")
    private String name;

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

}
