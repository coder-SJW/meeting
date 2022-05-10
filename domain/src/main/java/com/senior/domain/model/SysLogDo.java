package com.senior.domain.model;

import com.senior.common.base.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统日志(SysLog) Do
 *
 * @author senior
 * @since 2020-11-30 17:20:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLogDo implements BaseDo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 账号
     */
    private String passport;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 请求耗时(单位毫秒)
     */
    private Long cost;

    /**
     * 创建时间
     */
    private Long createTime;

}
