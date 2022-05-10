package com.senior.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.MeetingDao;
import com.senior.domain.bo.request.MeetingCreateBoRequest;
import com.senior.domain.bo.request.MeetingQueryBoRequest;
import com.senior.domain.bo.request.MeetingUpdateBoRequest;
import com.senior.domain.bo.response.MeetingQueryBoResponse;
import com.senior.domain.model.MeetingDo;
import com.senior.service.MeetingService;

import lombok.extern.slf4j.Slf4j;

/**
 * 会议室(Meeting) service接口实现类
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Slf4j
@Service
public class MeetingServiceImpl implements MeetingService {
    public static final int SUCCESS = 1;

    @Resource
    private MeetingDao dao;

    @Transactional
    @Override
    public boolean create(MeetingCreateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        MeetingDo model = BeanCopierKits.copyProperties(request, MeetingDo.class);
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
    public boolean updateById(MeetingUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, MeetingDo.class)) == SUCCESS;
    }

    @Override
    public MeetingQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), MeetingQueryBoResponse.class);
    }

    @Override
    public List<MeetingQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), MeetingQueryBoResponse.class);
    }

    @Override
    public List<MeetingQueryBoResponse> queryList(MeetingQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryList(request);
    }

    @Override
    public List<MeetingQueryBoResponse> groupBy(MeetingQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.groupBy(request);
    }

    @Override
    public long queryCount(MeetingQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }

}
