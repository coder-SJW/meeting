package com.senior.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senior.domain.bo.request.DepartmentQueryBoRequest;
import com.senior.domain.bo.response.DepartmentQueryBoResponse;
import com.senior.domain.model.DepartmentDo;

/**
 * 部门(Department) Dao
 *
 * @author senior
 * @since 2021-08-17 11:21:22
 */
public interface DepartmentDao {

    /**
     * 插入一条数据
     *
     * @param model
     * @return
     */
    int insert(DepartmentDo model);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据id更新一条数据
     *
     * @param record
     * @return
     */
    int updateById(DepartmentDo record);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    DepartmentDo getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<DepartmentDo> getByIds(@Param("ids") List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<DepartmentQueryBoResponse> queryList(DepartmentQueryBoRequest request);

    /**
     * 根据条件查询数据并按时间聚合
     *
     * @param request
     * @return
     */
    List<DepartmentQueryBoResponse> groupBy(DepartmentQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    long queryCount(DepartmentQueryBoRequest request);

}
