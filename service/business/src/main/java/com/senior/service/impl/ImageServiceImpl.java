package com.senior.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.ImageDao;
import com.senior.domain.bo.request.ImageCreateBoRequest;
import com.senior.domain.bo.request.ImageQueryBoRequest;
import com.senior.domain.bo.request.ImageUpdateBoRequest;
import com.senior.domain.bo.response.ImageBo;
import com.senior.domain.bo.response.ImageQueryBoResponse;
import com.senior.domain.model.ImageDo;
import com.senior.service.ImageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 图片(Image) service接口实现类
 *
 * @author senior
 * @since 2021-08-16 22:13:18
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    public static final int SUCCESS = 1;

    @Resource
    private ImageDao dao;

    @Transactional
    @Override
    public boolean create(ImageCreateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        ImageDo model = BeanCopierKits.copyProperties(request, ImageDo.class);
        boolean result = dao.insert(model) == SUCCESS;
        request.setId(model.getId());
        return result;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return dao.deleteById(id) == SUCCESS;
    }

    @Transactional
    @Override
    public boolean updateById(ImageUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, ImageDo.class)) == SUCCESS;
    }

    @Override
    public ImageQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), ImageQueryBoResponse.class);
    }

    @Override
    public List<ImageQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), ImageQueryBoResponse.class);
    }

    @Override
    public List<ImageQueryBoResponse> queryList(ImageQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryList(request);
    }

    @Override
    public List<ImageQueryBoResponse> groupBy(ImageQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.groupBy(request);
    }

    @Override
    public long queryCount(ImageQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }

    @Override
    public List<ImageBo> queryImages(List<Long> ids) {
        PreconditionsKits.checkNotNull(ids, "ids不能为空");
        List<ImageQueryBoResponse> list = getByIds(ids);
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        List<ImageBo> imageBos = Lists.newArrayList();
        list.forEach(
                o -> imageBos.add(ImageBo.builder().imageId(o.getId()).url(o.getData()).name(o.getName()).build()));
        return imageBos;
    }

}
