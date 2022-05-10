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
 * 用户角色表(Role) QueryVoRequest
 *
 * @author senior
 * @since 2020-11-29 12:12:57
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleQueryVoRequest implements BaseVoRequest {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String description;

    /**
     * 权限列表
     */
    @ApiModelProperty(value = "权限列表")
    private List<String> authorities;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Long updateTime;

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
