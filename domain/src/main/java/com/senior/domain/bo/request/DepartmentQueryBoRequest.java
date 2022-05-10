package com.senior.domain.bo.request;

import java.util.List;

import com.senior.common.base.PageQuery;
import com.senior.common.enums.GroupType;
import com.senior.domain.model.DepartmentDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 部门(Department) QueryBoRequest
 *
 * @author senior
 * @since 2021-08-17 11:21:22
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class DepartmentQueryBoRequest extends DepartmentDo implements PageQuery {

    /**
     * id列表
     */
    private List<Long> ids;

    /**
     * 部门
     */
    private String name;

    private Long startCreateTime;
    private Long endCreateTime;

    private Long startUpdateTime;
    private Long endUpdateTime;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 聚合维度
     */
    private GroupType groupType;
    /**
     * 聚合字段
     */
    private List<String> groupFields;

}
