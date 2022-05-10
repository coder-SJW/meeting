package com.senior.service;

import java.util.List;

import com.senior.domain.bo.request.MeetingCreateBoRequest;
import com.senior.domain.bo.request.MeetingQueryBoRequest;
import com.senior.domain.bo.request.MeetingUpdateBoRequest;
import com.senior.domain.bo.response.MeetingQueryBoResponse;

/**
 * 会议室(Meeting) service接口
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
public interface MeetingService {

    /**
     * 插入一条数据
     *
     * @param request
     * @return
     */
    boolean create(MeetingCreateBoRequest request);

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
    boolean updateById(MeetingUpdateBoRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    MeetingQueryBoResponse getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<MeetingQueryBoResponse> getByIds(List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<MeetingQueryBoResponse> queryList(MeetingQueryBoRequest request);

    /**
     * 根据条件查询数据并按时间聚合
     *
     * @param request
     * @return
     */
    List<MeetingQueryBoResponse> groupBy(MeetingQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    long queryCount(MeetingQueryBoRequest request);

}
