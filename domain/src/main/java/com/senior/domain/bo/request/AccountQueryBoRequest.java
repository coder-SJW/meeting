package com.senior.domain.bo.request;

import java.util.List;

import com.senior.common.base.PageQuery;
import com.senior.domain.model.AccountDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户账号表(Account) QueryBoRequest
 *
 * @author senior
 * @since 2020-12-02 20:44:26
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class AccountQueryBoRequest extends AccountDo implements PageQuery {

    /**
     * id列表
     */
    private List<Long> ids;
    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 开始登陆时间
     */
    private Long startLastLoginTime;
    /**
     * 截止登陆时间
     */
    private Long endLastLoginTime;

    /**
     * 开始创建时间
     */
    private Long startCreateTime;
    /**
     * 截止创建时间
     */
    private Long endCreateTime;

}
