package com.senior.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.AccountDao;
import com.senior.domain.bo.request.AccountCreateBoRequest;
import com.senior.domain.bo.request.AccountQueryBoRequest;
import com.senior.domain.bo.request.AccountUpdateBoRequest;
import com.senior.domain.bo.request.ChangePasswordBoRequest;
import com.senior.domain.bo.response.AccountQueryBoResponse;
import com.senior.domain.model.AccountDo;
import com.senior.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户账号表(Account) service接口实现类
 *
 * @author senior
 * @since 2020-11-29 12:12:41
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    public static final int SUCCESS = 1;

    @Resource
    private AccountDao dao;

    @Transactional
    @Override
    public boolean create(AccountCreateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.insert(BeanCopierKits.copyProperties(request, AccountDo.class)) == SUCCESS;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return dao.deleteById(id) == SUCCESS;
    }

    @Transactional
    @Override
    public boolean updateById(AccountUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, AccountDo.class)) == SUCCESS;
    }

    @Override
    public AccountQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), AccountQueryBoResponse.class);
    }

    @Override
    public AccountQueryBoResponse getByPassport(String passport) {
        PreconditionsKits.checkNotBlank(passport, "passport不能为空");
        return BeanCopierKits.copyProperties(dao.getByPassport(passport), AccountQueryBoResponse.class);
    }

    @Override
    public List<AccountQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), AccountQueryBoResponse.class);
    }

    @Override
    public List<AccountQueryBoResponse> queryList(AccountQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return BeanCopierKits.copyProperties(dao.queryList(request), AccountQueryBoResponse.class);
    }

    @Override
    public Long queryCount(AccountQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }

    @Override
    public int changePassword(ChangePasswordBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.changePassword(request);
    }

}
