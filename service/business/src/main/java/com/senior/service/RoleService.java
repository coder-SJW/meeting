package com.senior.service;

import java.util.List;

import com.senior.domain.bo.request.RoleCreateBoRequest;
import com.senior.domain.bo.request.RoleQueryBoRequest;
import com.senior.domain.bo.request.RoleUpdateBoRequest;
import com.senior.domain.bo.response.RoleQueryBoResponse;

/**
 * 用户角色表(Role) service接口
 *
 * @author senior
 * @since 2020-11-29 12:12:51
 */
public interface RoleService {

    /**
     * 插入一条数据
     *
     * @param request
     * @return
     */
    boolean create(RoleCreateBoRequest request);

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
    boolean updateById(RoleUpdateBoRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RoleQueryBoResponse getById(Long id);

    /**
     * 获取系统默认角色
     * 
     * @return
     */
    RoleQueryBoResponse getDefaultRole();

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<RoleQueryBoResponse> getByIds(List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<RoleQueryBoResponse> queryList(RoleQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    Long queryCount(RoleQueryBoRequest request);

}
