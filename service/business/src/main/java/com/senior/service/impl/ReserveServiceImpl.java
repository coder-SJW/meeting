package com.senior.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.ReserveDao;
import com.senior.domain.bo.request.ReserveCreateBoRequest;
import com.senior.domain.bo.request.ReserveQueryBoRequest;
import com.senior.domain.bo.request.ReserveUpdateBoRequest;
import com.senior.domain.bo.response.ReserveQueryBoResponse;
import com.senior.domain.model.ReserveDo;
import com.senior.service.ReserveService;

import lombok.extern.slf4j.Slf4j;

/**
 * 预约记录(Reserve) service接口实现类
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Slf4j
@Service
public class ReserveServiceImpl implements ReserveService {
    public static final int SUCCESS = 1;

    @Resource
    private ReserveDao dao;

    @Transactional
    @Override
    public boolean create(ReserveCreateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        ReserveDo model = BeanCopierKits.copyProperties(request, ReserveDo.class);
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
    public boolean updateById(ReserveUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, ReserveDo.class)) == SUCCESS;
    }

    @Override
    public ReserveQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), ReserveQueryBoResponse.class);
    }

    @Override
    public ReserveQueryBoResponse getByMeetingId(Long id) {
        return BeanCopierKits.copyProperties(dao.getByMeetingId(id), ReserveQueryBoResponse.class);
    }

    @Override
    public List<ReserveQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), ReserveQueryBoResponse.class);
    }

    @Override
    public List<ReserveQueryBoResponse> queryList(ReserveQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryList(request);
    }

    @Override
    public List<ReserveQueryBoResponse> queryMyEvents(ReserveQueryBoRequest request) {
        return dao.queryMyEvents(request);
    }

    @Override
    public List<ReserveQueryBoResponse> groupBy(ReserveQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.groupBy(request);
    }

    @Override
    public long queryCount(ReserveQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }

}
