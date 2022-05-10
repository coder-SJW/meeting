package com.senior.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senior.domain.bo.request.SysLogQueryBoRequest;
import com.senior.domain.model.SysLogDo;

/**
 * 系统日志(SysLog) Dao
 *
 * @author senior
 * @since 2020-11-30 17:20:23
 */
public interface SysLogDao {

    /**
     * 插入一条数据
     *
     * @param model
     * @return
     */
    int insert(SysLogDo model);

    /**
     * 批量插入数据
     * 
     * @param models
     * @return
     */
    int batchInsert(List<SysLogDo> models);

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
    int updateById(SysLogDo record);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    SysLogDo getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<SysLogDo> getByIds(@Param("ids") List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<SysLogDo> queryList(SysLogQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    Long queryCount(SysLogQueryBoRequest request);

}
