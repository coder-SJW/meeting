package com.senior.console.api.controller.request;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.senior.common.base.BaseVoRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片(Image) UpdateVoRequest
 *
 * @author senior
 * @since 2021-08-16 22:13:18
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageUpdateVoRequest implements BaseVoRequest {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 图片base64
     */
    @ApiModelProperty(value = "图片base64")
    private String data;

    /**
     * 图片名称
     */
    @ApiModelProperty(value = "图片名称")
    @Length(max = 255, message = "name长度不能超过255")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Long updateTime;

    private List<Long> imageIds;

}
