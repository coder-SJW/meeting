package com.senior.domain.model;

import com.senior.common.base.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户账号表(Account) Do
 *
 * @author senior
 * @since 2020-12-02 20:44:25
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDo implements BaseDo {

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
     * 密码
     */
    private String password;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 角色id
     */
    private Long roleId;

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
     * 最近登录时间
     */
    private Long lastLoginTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 所属部门
     */
    private Long departmentId;

}
