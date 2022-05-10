package com.senior.console.api.controller.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.senior.common.base.BaseVoRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色表(Role) CreateVoRequest
 *
 * @author senior
 * @since 2020-11-29 12:12:58
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreateVoRequest implements BaseVoRequest {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @Length(max = 6, message = "name长度不能超过6")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 128, message = "description长度不能超过128")
    private String description;

    /**
     * 权限列表
     */
    @ApiModelProperty(value = "权限列表")
    @NotEmpty(message = "权限不能为空")
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

}
