package com.senior.domain.bo.request;

import java.util.List;

import com.senior.common.base.PageQuery;
import com.senior.domain.model.RoleDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户角色表(Role) QueryBoRequest
 *
 * @author senior
 * @since 2020-11-29 12:12:55
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class RoleQueryBoRequest extends RoleDo implements PageQuery {

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

}
