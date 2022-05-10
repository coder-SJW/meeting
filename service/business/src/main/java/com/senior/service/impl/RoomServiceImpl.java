package com.senior.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.RoomDao;
import com.senior.domain.bo.request.RoomCreateBoRequest;
import com.senior.domain.bo.request.RoomQueryBoRequest;
import com.senior.domain.bo.request.RoomUpdateBoRequest;
import com.senior.domain.bo.response.RoomQueryBoResponse;
import com.senior.domain.model.RoomDo;
import com.senior.service.RoomService;

import lombok.extern.slf4j.Slf4j;

/**
 * 会议室(Room) service接口实现类
 *
 * @author sjw
 */
@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
    public static final int SUCCESS = 1;

    @Resource
    private RoomDao dao;

    @Transactional
    @Override
    public boolean create(RoomCreateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        RoomDo model = BeanCopierKits.copyProperties(request, RoomDo.class);
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
    public boolean updateById(RoomUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, RoomDo.class)) == SUCCESS;
    }

    @Override
    public RoomQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), RoomQueryBoResponse.class);
    }

    @Override
    public List<RoomQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), RoomQueryBoResponse.class);
    }

    @Override
    public List<RoomQueryBoResponse> queryList(RoomQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryList(request);
    }

    @Override
    public List<RoomQueryBoResponse> groupBy(RoomQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.groupBy(request);
    }

    @Override
    public long queryCount(RoomQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }

}
