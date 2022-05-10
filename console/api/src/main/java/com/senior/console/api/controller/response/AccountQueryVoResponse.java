package com.senior.console.api.controller.response;

import com.senior.common.base.BaseVoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账号表(Account) QueryVoResponse
 *
 * @author senior
 * @since 2020-12-02 20:44:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountQueryVoResponse implements BaseVoResponse {

    /**
     * 主键
     */
    private Long id;

    /**
     * 账号
     */
    private String passport;

    /**
     * 名称
     */
    private String name;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 性别 0：女；1：男
     */
    private Integer sex;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 最近登录时间
     */
    private Long lastLoginTime;
    /**
     * 所属部门
     */
    private Long departmentId;
    /**
     * 部门名称
     */
    private String departmentName;

}
