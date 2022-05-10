package com.senior.console.api.controller.request;

import javax.validation.constraints.NotNull;

import com.senior.common.base.BaseVoRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报修(Repair) UpdateVoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepairUpdateVoRequest implements BaseVoRequest {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 状态;1:未处理 2:处理中 3: 已完成
     */
    @ApiModelProperty(value = "状态;1:未处理 2:处理中 3: 已完成")
    @NotNull
    private Integer status;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @NotNull
    private String content;

    /**
     * 会议室
     */
    @ApiModelProperty(value = "会议室")
    @NotNull
    private Long roomId;

    /**
     * 报修人
     */
    @ApiModelProperty(value = "报修人")
    @NotNull
    private Long accountId;

}
