package com.senior.console.api.controller.request;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.senior.common.base.BaseVoRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报修(Repair) QueryVoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepairQueryVoRequest implements BaseVoRequest {
    /**
     * id列表
     */
    @ApiModelProperty(value = "id列表")
    private List<Long> ids;

    /**
     * 状态;1:未处理 2:处理中 3: 已完成
     */
    @ApiModelProperty(value = "状态;1:未处理 2:处理中 3: 已完成")
    private Integer status;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    private Long startCreateTime;
    private Long endCreateTime;

    private Long startUpdateTime;
    private Long endUpdateTime;

    /**
     * 会议室
     */
    @ApiModelProperty(value = "会议室")
    private String roomName;

    /**
     * 报修人
     */
    @ApiModelProperty(value = "报修人")
    private String accountPassport;

    private Long accountId;
    private Long roomId;

    /**
     * 每页条数
     */
    @Min(value = 1, message = "pageSize必须大于1")
    @Max(value = 100, message = "pageSize必须小于100")
    @NotNull
    private Integer pageSize;

    /**
     * 当前页码
     */
    @NotNull
    @Min(value = 1, message = "pageNum必须大于0")
    private Integer pageNum;

}
