package com.senior.service;

import java.util.List;

import com.senior.domain.bo.request.SysLogCreateBoRequest;
import com.senior.domain.bo.request.SysLogQueryBoRequest;
import com.senior.domain.bo.request.SysLogUpdateBoRequest;
import com.senior.domain.bo.response.SysLogQueryBoResponse;

/**
 * 系统日志(SysLog) service接口
 *
 * @author senior
 * @since 2020-11-30 17:20:23
 */
public interface SysLogService {

    /**
     * 插入一条数据
     *
     * @param request
     * @return
     */
    boolean create(SysLogCreateBoRequest request);

    /**
     * 批量插入数据
     * 
     * @param requests
     * @return
     */
    int batchCreate(List<SysLogCreateBoRequest> requests);

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
    boolean updateById(SysLogUpdateBoRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    SysLogQueryBoResponse getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<SysLogQueryBoResponse> getByIds(List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<SysLogQueryBoResponse> queryList(SysLogQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    Long queryCount(SysLogQueryBoRequest request);

}
