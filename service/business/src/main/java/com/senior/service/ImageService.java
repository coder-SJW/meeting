package com.senior.service;

import java.util.List;

import com.senior.domain.bo.request.ImageCreateBoRequest;
import com.senior.domain.bo.request.ImageQueryBoRequest;
import com.senior.domain.bo.request.ImageUpdateBoRequest;
import com.senior.domain.bo.response.ImageBo;
import com.senior.domain.bo.response.ImageQueryBoResponse;

/**
 * 图片(Image) service接口
 *
 * @author senior
 * @since 2021-08-16 22:13:18
 */
public interface ImageService {

    /**
     * 插入一条数据
     *
     * @param request
     * @return
     */
    boolean create(ImageCreateBoRequest request);

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
    boolean updateById(ImageUpdateBoRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ImageQueryBoResponse getById(Long id);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<ImageQueryBoResponse> getByIds(List<Long> ids);

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

    /**
     * 根据ID列表查询 imageBo
     * 
     * @param ids
     * @return
     */
    List<ImageBo> queryImages(List<Long> ids);

}
