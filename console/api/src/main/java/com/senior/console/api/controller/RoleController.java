package com.senior.console.api.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senior.common.Page;
import com.senior.common.Result;
import com.senior.common.enums.RoleType;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.console.api.controller.request.RoleCreateVoRequest;
import com.senior.console.api.controller.request.RoleQueryVoRequest;
import com.senior.console.api.controller.request.RoleUpdateVoRequest;
import com.senior.console.api.controller.response.RoleQueryVoResponse;
import com.senior.domain.bo.request.RoleCreateBoRequest;
import com.senior.domain.bo.request.RoleQueryBoRequest;
import com.senior.domain.bo.request.RoleUpdateBoRequest;
import com.senior.domain.bo.response.RoleQueryBoResponse;
import com.senior.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户角色表(Role) Controller
 *
 * @author senior
 * @since 2020-11-29 13:43:21
 */
@Api(tags = "用户角色表接口}")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Resource
    private RoleService service;

    @ApiOperation(value = "新增接口", notes = "根据接口参数新增一条数据")
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROLE_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody RoleCreateVoRequest request) {
        RoleCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, RoleCreateBoRequest.class);
        RoleQueryBoRequest query = new RoleQueryBoRequest();
        query.setName(request.getName());
        PreconditionsKits.checkArgs(CollectionUtils.isEmpty(service.queryList(query)), "角色名称不能重复");
        boRequest.setCreateTime(System.currentTimeMillis());
        boRequest.setUpdateTime(System.currentTimeMillis());
        // 只允许创建自定义角色
        boRequest.setType(RoleType.CUSTOM_ROLE.getCode());
        boolean success = service.create(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("创建失败");
    }

    @ApiOperation(value = "删除接口", notes = "根据id删除目标数据")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROLE_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        RoleQueryBoResponse role = service.getById(id);
        PreconditionsKits.checkNotNull(role, "角色不存在");
        PreconditionsKits.checkArgs(role.getType().equals(RoleType.CUSTOM_ROLE.getCode()), "系统默认角色不允许删除");
        boolean success = service.deleteById(id);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "修改接口", notes = "根据id修改目标数据")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROLE_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody RoleUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        request.setId(id);
        RoleUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, RoleUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        boolean success = service.updateById(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "单条查询接口", notes = "根据id查询一条目标数据")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROLE_QUERY.name())")
    public Result<RoleQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        RoleQueryBoResponse boResponse = service.getById(id);
        RoleQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse, RoleQueryVoResponse.class);
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROLE_QUERY.name())")
    public Result<Page<RoleQueryVoResponse>> pageQuery(RoleQueryVoRequest request) {
        RoleQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, RoleQueryBoRequest.class);
        Long count = service.queryCount(boRequest);
        if (count == null || count == 0) {
            return Result.ok(Page.empty());
        }
        List<RoleQueryBoResponse> responses = service.queryList(boRequest);
        List<RoleQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses, RoleQueryVoResponse.class);
        return Result.ok(Page.fill(count, voResponses));
    }
}
