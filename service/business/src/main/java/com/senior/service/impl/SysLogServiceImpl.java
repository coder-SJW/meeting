package com.senior.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.SysLogDao;
import com.senior.domain.bo.request.SysLogCreateBoRequest;
import com.senior.domain.bo.request.SysLogQueryBoRequest;
import com.senior.domain.bo.request.SysLogUpdateBoRequest;
import com.senior.domain.bo.response.SysLogQueryBoResponse;
import com.senior.domain.model.SysLogDo;
import com.senior.service.SysLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统日志(SysLog) service接口实现类
 *
 * @author senior
 * @since 2020-11-30 17:20:23
 */
@Slf4j
@Service
public class SysLogServiceImpl implements SysLogService {
    public static final int SUCCESS = 1;

    @Resource
    private SysLogDao dao;

    @Transactional
    @Override
    public boolean create(SysLogCreateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.insert(BeanCopierKits.copyProperties(request, SysLogDo.class)) == SUCCESS;
    }

    @Override
    public int batchCreate(List<SysLogCreateBoRequest> requests) {
        PreconditionsKits.checkNotNull(requests, "参数不能为空");
        return dao.batchInsert(BeanCopierKits.copyProperties(requests, SysLogDo.class));
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return dao.deleteById(id) == SUCCESS;
    }

    @Transactional
    @Override
    public boolean updateById(SysLogUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, SysLogDo.class)) == SUCCESS;
    }

    @Override
    public SysLogQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), SysLogQueryBoResponse.class);
    }

    @Override
    public List<SysLogQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), SysLogQueryBoResponse.class);
    }

    @Override
    public List<SysLogQueryBoResponse> queryList(SysLogQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return BeanCopierKits.copyProperties(dao.queryList(request), SysLogQueryBoResponse.class);
    }

    @Override
    public Long queryCount(SysLogQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }

}
