package com.senior.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senior.domain.bo.request.ReserveQueryBoRequest;
import com.senior.domain.bo.response.ReserveQueryBoResponse;
import com.senior.domain.model.ReserveDo;

/**
 * 预约记录(Reserve) Dao
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
public interface ReserveDao {

    /**
     * 插入一条数据
     *
     * @param model
     * @return
     */
    int insert(ReserveDo model);

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
    int updateById(ReserveDo record);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ReserveDo getById(Long id);

    /**
     * 根据会议ID查询
     *
     * @param id
     * @return
     */
    ReserveDo getByMeetingId(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<ReserveDo> getByIds(@Param("ids") List<Long> ids);

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
