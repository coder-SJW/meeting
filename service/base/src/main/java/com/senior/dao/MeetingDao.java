package com.senior.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senior.domain.bo.request.MeetingQueryBoRequest;
import com.senior.domain.bo.response.MeetingQueryBoResponse;
import com.senior.domain.model.MeetingDo;

/**
 * 会议室(Meeting) Dao
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
public interface MeetingDao {

    /**
     * 插入一条数据
     *
     * @param model
     * @return
     */
    int insert(MeetingDo model);

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
    int updateById(MeetingDo record);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    MeetingDo getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<MeetingDo> getByIds(@Param("ids") List<Long> ids);

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
