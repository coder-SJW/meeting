package com.senior.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senior.domain.bo.request.RepairQueryBoRequest;
import com.senior.domain.bo.response.RepairQueryBoResponse;
import com.senior.domain.model.RepairDo;

/**
 * 报修(Repair) Dao
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
public interface RepairDao {

    /**
     * 插入一条数据
     *
     * @param model
     * @return
     */
    int insert(RepairDo model);

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
    int updateById(RepairDo record);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RepairDo getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<RepairDo> getByIds(@Param("ids") List<Long> ids);

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
