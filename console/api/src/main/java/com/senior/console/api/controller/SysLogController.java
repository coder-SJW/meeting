package com.senior.console.api.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senior.common.Page;
import com.senior.common.Result;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.console.api.controller.request.SysLogQueryVoRequest;
import com.senior.console.api.controller.response.SysLogQueryVoResponse;
import com.senior.console.api.security.LoginInfoService;
import com.senior.domain.bo.request.SysLogQueryBoRequest;
import com.senior.domain.bo.response.SysLogQueryBoResponse;
import com.senior.service.SysLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统日志(SysLog) Controller
 *
 * @author senior
 * @since 2020-11-30 17:20:23
 */
@Api(tags = "系统日志接口}")
@RestController
@RequestMapping("/api/sysLog")
public class SysLogController {

    @Resource
    private SysLogService service;
    @Resource
    private LoginInfoService loginInfoService;

    @ApiOperation(value = "单条查询接口", notes = "根据id查询一条目标数据")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).SYS_LOG_QUERY.name())")
    public Result<SysLogQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        SysLogQueryBoResponse boResponse = service.getById(id);
        SysLogQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse, SysLogQueryVoResponse.class);
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).SYS_LOG_QUERY.name())")
    public Result<Page<SysLogQueryVoResponse>> pageQuery(SysLogQueryVoRequest request) {
        SysLogQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, SysLogQueryBoRequest.class);
        Long count = service.queryCount(boRequest);
        if (count == null || count == 0) {
            return Result.ok(Page.empty());
        }
        List<SysLogQueryBoResponse> responses = service.queryList(boRequest);
        List<SysLogQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses, SysLogQueryVoResponse.class);
        return Result.ok(Page.fill(count, voResponses));
    }
}
