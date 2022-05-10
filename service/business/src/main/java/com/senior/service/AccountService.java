package com.senior.service;

import java.util.List;

import com.senior.domain.bo.request.AccountCreateBoRequest;
import com.senior.domain.bo.request.AccountQueryBoRequest;
import com.senior.domain.bo.request.AccountUpdateBoRequest;
import com.senior.domain.bo.request.ChangePasswordBoRequest;
import com.senior.domain.bo.response.AccountQueryBoResponse;

/**
 * 用户账号表(Account) service接口
 *
 * @author sjw
 */
public interface AccountService {

    /**
     * 插入一条数据
     *
     * @param request
     * @return
     */
    boolean create(AccountCreateBoRequest request);

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
    boolean updateById(AccountUpdateBoRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    AccountQueryBoResponse getById(Long id);

    /**
     * 根据passport查询
     * 
     * @param passport
     * @return
     */
    AccountQueryBoResponse getByPassport(String passport);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<AccountQueryBoResponse> getByIds(List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<AccountQueryBoResponse> queryList(AccountQueryBoRequest request);

    /**
     * 根据条件查询数据条数
     *
     * @param request
     * @return
     */
    Long queryCount(AccountQueryBoRequest request);

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    int changePassword(ChangePasswordBoRequest request);

}
