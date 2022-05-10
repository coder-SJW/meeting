package com.senior.console.api.controller.request;

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
 * 系统日志(SysLog) QueryVoRequest
 *
 * @author senior
 * @since 2020-11-30 17:20:24
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLogQueryVoRequest implements BaseVoRequest {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String passport;

    /**
     * 请求URL
     */
    @ApiModelProperty(value = "请求URL")
    private String url;

    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法")
    private String method;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String params;

    /**
     * 请求ip
     */
    @ApiModelProperty(value = "请求ip")
    private String ip;

    /**
     * 请求耗时(单位毫秒)
     */
    @ApiModelProperty(value = "请求耗时(单位毫秒)")
    private Long cost;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Long createTime;

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
