package com.senior.console.api.controller.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.senior.common.base.BaseVoRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会议室(Meeting) CreateVoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingCreateVoRequest implements BaseVoRequest {

    /**
     * 会议名称
     */
    @ApiModelProperty(value = "会议名称")
    @Length(max = 255, message = "name长度不能超过255")
    @NotNull
    private String name;

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
     * 人员名单
     */
    @ApiModelProperty(value = "人员名单")
    private List<Long> accountIds;

    /**
     * 开始时间
     */
    private Long reserveStartTime;

    /**
     * 结束时间
     */
    private Long reserveEndTime;

}
