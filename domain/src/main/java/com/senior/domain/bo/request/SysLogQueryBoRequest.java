package com.senior.domain.bo.request;

import java.util.List;

import com.senior.common.base.PageQuery;
import com.senior.domain.model.SysLogDo;

import lombok.Data;

/**
 * 系统日志(SysLog) QueryBoRequest
 *
 * @author senior
 * @since 2020-11-30 17:20:24
 */
@Data
public class SysLogQueryBoRequest extends SysLogDo implements PageQuery {

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
