package com.senior.console.api.controller.response;

import com.senior.common.base.BaseVoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统日志(SysLog) QueryVoResponse
 *
 * @author senior
 * @since 2020-11-30 17:20:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLogQueryVoResponse implements BaseVoResponse {

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
