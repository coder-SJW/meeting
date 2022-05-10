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
 * 用户账号表(Account) QueryVoRequest
 *
 * @author senior
 * @since 2020-12-02 20:44:26
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountQueryVoRequest implements BaseVoRequest {

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
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private Integer status;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    private Long departmentId;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 性别 0：女；1：男
     */
    @ApiModelProperty(value = "性别 0：女；1：男")
    private Integer sex;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String description;

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
