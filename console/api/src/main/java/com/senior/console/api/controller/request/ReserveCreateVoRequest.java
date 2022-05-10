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
 * 预约记录(Reserve) CreateVoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveCreateVoRequest implements BaseVoRequest {

    /**
     * 会议室
     */
    @ApiModelProperty(value = "会议室")
    @NotNull
    private Long roomId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 预约人
     */
    @ApiModelProperty(value = "预约人")
    @NotNull
    private Long accountId;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @NotNull
    private Long reserveStartTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @NotNull
    private Long reserveEndTime;

}
