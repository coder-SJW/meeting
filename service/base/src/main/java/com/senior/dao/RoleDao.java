package com.senior.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senior.domain.bo.request.RoleQueryBoRequest;
import com.senior.domain.model.RoleDo;

/**
 * 用户角色表(Role) Dao
 *
 * @author senior
 * @since 2020-11-29 14:12:12
 */
public interface RoleDao {

    /**
     * 插入一条数据
     *
     * @param model
     * @return
     */
    int insert(RoleDo model);

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
    int updateById(RoleDo record);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RoleDo getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<RoleDo> getByIds(@Param("ids") List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<RoleDo> queryList(RoleQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    Long queryCount(RoleQueryBoRequest request);

}
