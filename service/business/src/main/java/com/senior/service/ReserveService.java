package com.senior.service;

import java.util.List;

import com.senior.domain.bo.request.ReserveCreateBoRequest;
import com.senior.domain.bo.request.ReserveQueryBoRequest;
import com.senior.domain.bo.request.ReserveUpdateBoRequest;
import com.senior.domain.bo.response.ReserveQueryBoResponse;

/**
 * 预约记录(Reserve) service接口
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
public interface ReserveService {

    /**
     * 插入一条数据
     *
     * @param request
     * @return
     */
    boolean create(ReserveCreateBoRequest request);

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
    boolean updateById(ReserveUpdateBoRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ReserveQueryBoResponse getById(Long id);

    /**
     * 根据会议ID查询
     *
     * @param id
     * @return
     */
    ReserveQueryBoResponse getByMeetingId(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<ReserveQueryBoResponse> getByIds(List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<ReserveQueryBoResponse> queryList(ReserveQueryBoRequest request);

    List<ReserveQueryBoResponse> queryMyEvents(ReserveQueryBoRequest request);

    /**
     * 根据条件查询数据并按时间聚合
     *
     * @param request
     * @return
     */
    List<ReserveQueryBoResponse> groupBy(ReserveQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    long queryCount(ReserveQueryBoRequest request);

}
