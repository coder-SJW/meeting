package com.senior.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senior.domain.bo.request.ImageQueryBoRequest;
import com.senior.domain.bo.response.ImageQueryBoResponse;
import com.senior.domain.model.ImageDo;

/**
 * 图片(Image) Dao
 *
 * @author senior
 * @since 2021-08-16 22:13:18
 */
public interface ImageDao {

    /**
     * 插入一条数据
     *
     * @param model
     * @return
     */
    int insert(ImageDo model);

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
    int updateById(ImageDo record);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ImageDo getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<ImageDo> getByIds(@Param("ids") List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<ImageQueryBoResponse> queryList(ImageQueryBoRequest request);

    /**
     * 根据条件查询数据并按时间聚合
     *
     * @param request
     * @return
     */
    List<ImageQueryBoResponse> groupBy(ImageQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    long queryCount(ImageQueryBoRequest request);

}
