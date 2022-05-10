package com.senior.domain.model;

import java.util.List;

import com.senior.common.base.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户角色表(Role) Do
 *
 * @author senior
 * @since 2020-11-29 12:12:49
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDo implements BaseDo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 备注
     */
    private String description;

    /**
     * 权限列表
     */
    private List<String> authorities;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 角色类型 {@link com.senior.common.enums.RoleType}
     */
    private Integer type;

}
