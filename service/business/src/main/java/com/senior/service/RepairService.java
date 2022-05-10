package com.senior.service;

import java.util.List;

import com.senior.domain.bo.request.RepairCreateBoRequest;
import com.senior.domain.bo.request.RepairQueryBoRequest;
import com.senior.domain.bo.request.RepairUpdateBoRequest;
import com.senior.domain.bo.response.RepairQueryBoResponse;

/**
 * 报修(Repair) service接口
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
public interface RepairService {

    /**
     * 插入一条数据
     *
     * @param request
     * @return
     */
    boolean create(RepairCreateBoRequest request);

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
    boolean updateById(RepairUpdateBoRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RepairQueryBoResponse getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<RepairQueryBoResponse> getByIds(List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<RepairQueryBoResponse> queryList(RepairQueryBoRequest request);

    /**
     * 根据条件查询数据并按时间聚合
     *
     * @param request
     * @return
     */
    List<RepairQueryBoResponse> groupBy(RepairQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    long queryCount(RepairQueryBoRequest request);

}
