package com.senior.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senior.domain.bo.request.AccountQueryBoRequest;
import com.senior.domain.bo.request.ChangePasswordBoRequest;
import com.senior.domain.model.AccountDo;

/**
 * 用户账号表(Account) Dao
 *
 * @author senior
 * @since 2020-12-02 20:44:26
 */
public interface AccountDao {

    /**
     * 插入一条数据
     *
     * @param model
     * @return
     */
    int insert(AccountDo model);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据id更新一条数据
     *
     * @param record
     * @return
     */
    int updateById(AccountDo record);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    AccountDo getById(Long id);

    /**
     * 根据passport查询
     *
     * @param passport
     * @return
     */
    AccountDo getByPassport(String passport);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<AccountDo> getByIds(@Param("ids") List<Long> ids);

    /**
     * 根据条件查询数据列表
     *
     * @param request
     * @return
     */
    List<AccountDo> queryList(AccountQueryBoRequest request);

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
