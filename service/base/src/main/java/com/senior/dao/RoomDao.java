package com.senior.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senior.domain.bo.request.RoomQueryBoRequest;
import com.senior.domain.bo.response.RoomQueryBoResponse;
import com.senior.domain.model.RoomDo;

/**
 * 会议室(Room) Dao
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
public interface RoomDao {

    /**
     * 插入一条数据
     *
     * @param model
     * @return
     */
    int insert(RoomDo model);

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
    int updateById(RoomDo record);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RoomDo getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<RoomDo> getByIds(@Param("ids") List<Long> ids);

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
