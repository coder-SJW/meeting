package com.senior.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.DepartmentDao;
import com.senior.domain.bo.request.DepartmentCreateBoRequest;
import com.senior.domain.bo.request.DepartmentQueryBoRequest;
import com.senior.domain.bo.request.DepartmentUpdateBoRequest;
import com.senior.domain.bo.response.DepartmentQueryBoResponse;
import com.senior.domain.model.DepartmentDo;
import com.senior.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;

/**
 * 部门(Department) service接口实现类
 *
 * @author senior
 * @since 2021-08-17 11:21:22
 */
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {
    public static final int SUCCESS = 1;

    @Resource
    private DepartmentDao dao;

    @Transactional
    @Override
    public boolean create(DepartmentCreateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        DepartmentDo model = BeanCopierKits.copyProperties(request, DepartmentDo.class);
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
    public boolean updateById(DepartmentUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, DepartmentDo.class)) == SUCCESS;
    }

    @Override
    public DepartmentQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), DepartmentQueryBoResponse.class);
    }

    @Override
    public List<DepartmentQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), DepartmentQueryBoResponse.class);
    }

    @Override
    public List<DepartmentQueryBoResponse> queryList(DepartmentQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryList(request);
    }

    @Override
    public List<DepartmentQueryBoResponse> groupBy(DepartmentQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.groupBy(request);
    }

    @Override
    public long queryCount(DepartmentQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }

}
