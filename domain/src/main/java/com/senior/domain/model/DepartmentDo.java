package com.senior.domain.model;

import com.senior.common.base.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 部门(Department) Do
 *
 * @author senior
 * @since 2021-08-17 11:23:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDo implements BaseDo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 部门
     */
    private String name;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

}
