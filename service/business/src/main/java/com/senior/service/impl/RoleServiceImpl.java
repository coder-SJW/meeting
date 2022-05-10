package com.senior.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.senior.common.enums.RoleType;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.RoleDao;
import com.senior.domain.bo.request.RoleCreateBoRequest;
import com.senior.domain.bo.request.RoleQueryBoRequest;
import com.senior.domain.bo.request.RoleUpdateBoRequest;
import com.senior.domain.bo.response.RoleQueryBoResponse;
import com.senior.domain.model.RoleDo;
import com.senior.service.RoleService;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户角色表(Role) service接口实现类
 *
 * @author senior
 * @since 2020-11-29 12:12:52
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    public static final int SUCCESS = 1;

    @Resource
    private RoleDao dao;

    @Transactional
    @Override
    public boolean create(RoleCreateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.insert(BeanCopierKits.copyProperties(request, RoleDo.class)) == SUCCESS;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return dao.deleteById(id) == SUCCESS;
    }

    @Transactional
    @Override
    public boolean updateById(RoleUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, RoleDo.class)) == SUCCESS;
    }

    @Override
    public RoleQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), RoleQueryBoResponse.class);
    }

    @Override
    public RoleQueryBoResponse getDefaultRole() {
        List<RoleQueryBoResponse> roles = queryList(
                RoleQueryBoRequest.builder().type(RoleType.DEFAULT_ROLE.getCode()).build());
        PreconditionsKits.checkArgs(!CollectionUtils.isEmpty(roles), "系统默认角色不存在，请检查数据是否初始化");
        return roles.get(0);
    }

    @Override
    public List<RoleQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), RoleQueryBoResponse.class);
    }

    @Override
    public List<RoleQueryBoResponse> queryList(RoleQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return BeanCopierKits.copyProperties(dao.queryList(request), RoleQueryBoResponse.class);
    }

    @Override
    public Long queryCount(RoleQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }

}
