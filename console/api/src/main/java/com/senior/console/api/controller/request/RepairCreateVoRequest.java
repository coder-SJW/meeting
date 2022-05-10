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
 * 报修(Repair) CreateVoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepairCreateVoRequest implements BaseVoRequest {

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
