package com.senior.service;

import java.util.List;

import com.senior.domain.bo.request.RoomCreateBoRequest;
import com.senior.domain.bo.request.RoomQueryBoRequest;
import com.senior.domain.bo.request.RoomUpdateBoRequest;
import com.senior.domain.bo.response.RoomQueryBoResponse;

/**
 * 会议室(Room) service接口
 *
 * @author sjw
 */
public interface RoomService {

    /**
     * 插入一条数据
     *
     * @param request
     * @return
     */
    boolean create(RoomCreateBoRequest request);

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
    boolean updateById(RoomUpdateBoRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RoomQueryBoResponse getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<RoomQueryBoResponse> getByIds(List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<RoomQueryBoResponse> queryList(RoomQueryBoRequest request);

    /**
     * 根据条件查询数据并按时间聚合
     *
     * @param request
     * @return
     */
    List<RoomQueryBoResponse> groupBy(RoomQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    long queryCount(RoomQueryBoRequest request);

}
