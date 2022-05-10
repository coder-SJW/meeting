package com.senior.service;

import java.util.List;

import com.senior.domain.bo.request.DepartmentCreateBoRequest;
import com.senior.domain.bo.request.DepartmentQueryBoRequest;
import com.senior.domain.bo.request.DepartmentUpdateBoRequest;
import com.senior.domain.bo.response.DepartmentQueryBoResponse;

/**
 * 部门(Department) service接口
 *
 * @author senior
 * @since 2021-08-17 11:21:22
 */
public interface DepartmentService {

    /**
     * 插入一条数据
     *
     * @param request
     * @return
     */
    boolean create(DepartmentCreateBoRequest request);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 根据id更新一条数据
     *
     * @param request
     * @return
     */
    boolean updateById(DepartmentUpdateBoRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    DepartmentQueryBoResponse getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<DepartmentQueryBoResponse> getByIds(List<Long> ids);

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
