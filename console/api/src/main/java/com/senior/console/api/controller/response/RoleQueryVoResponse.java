package com.senior.console.api.controller.response;

import java.util.List;

import com.senior.common.base.BaseVoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色表(Role) QueryVoResponse
 *
 * @author senior
 * @since 2020-11-29 12:12:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleQueryVoResponse implements BaseVoResponse {

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
