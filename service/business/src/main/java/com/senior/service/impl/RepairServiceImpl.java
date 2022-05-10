package com.senior.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.RepairDao;
import com.senior.domain.bo.request.RepairCreateBoRequest;
import com.senior.domain.bo.request.RepairQueryBoRequest;
import com.senior.domain.bo.request.RepairUpdateBoRequest;
import com.senior.domain.bo.response.RepairQueryBoResponse;
import com.senior.domain.model.RepairDo;
import com.senior.service.RepairService;

import lombok.extern.slf4j.Slf4j;

/**
 * 报修(Repair) service接口实现类
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Slf4j
@Service
public class RepairServiceImpl implements RepairService {
    public static final int SUCCESS = 1;

    @Resource
    private RepairDao dao;

    @Transactional
    @Override
    public boolean create(RepairCreateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        RepairDo model = BeanCopierKits.copyProperties(request, RepairDo.class);
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
    public boolean updateById(RepairUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, RepairDo.class)) == SUCCESS;
    }

    @Override
    public RepairQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), RepairQueryBoResponse.class);
    }

    @Override
    public List<RepairQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), RepairQueryBoResponse.class);
    }

    @Override
    public List<RepairQueryBoResponse> queryList(RepairQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryList(request);
    }

    @Override
    public List<RepairQueryBoResponse> groupBy(RepairQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.groupBy(request);
    }

    @Override
    public long queryCount(RepairQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }

}
