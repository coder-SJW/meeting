package com.senior.console.api.controller.request;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

import com.senior.common.base.BaseVoRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账号表(Account) CreateVoRequest
 *
 * @author senior
 * @since 2020-12-19 16:48:07
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateVoRequest implements BaseVoRequest {

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @Length(max = 16, message = "passport长度不能超过16")
    private String passport;

    /**
     * 密码
     */
    private String password;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @Length(max = 6, message = "name长度不能超过6")
    private String name;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private Integer status;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    private Long roleId;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Length(max = 32, message = "email长度不能超过32")
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @Length(max = 16, message = "phone长度不能超过16")
    private String phone;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    @Length(max = 64, message = "address长度不能超过64")
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
    @Length(max = 128, message = "description长度不能超过128")
    private String description;

    /**
     * 最近登录时间
     */
    @ApiModelProperty(value = "最近登录时间")
    private Long lastLoginTime;

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
     * 所属部门
     */
    @ApiModelProperty(value = "部门ID")
    private Long departmentId;

}
