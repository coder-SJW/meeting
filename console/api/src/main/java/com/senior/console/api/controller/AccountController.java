package com.senior.console.api.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.senior.common.Page;
import com.senior.common.Result;
import com.senior.common.enums.AccountStatus;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.CollectionKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.console.api.controller.request.AccountCreateVoRequest;
import com.senior.console.api.controller.request.AccountQueryVoRequest;
import com.senior.console.api.controller.request.AccountUpdateVoRequest;
import com.senior.console.api.controller.request.ChangePasswordVoRequest;
import com.senior.console.api.controller.response.AccountQueryVoResponse;
import com.senior.console.api.controller.response.CurrentAccountVoResponse;
import com.senior.console.api.security.AccountUserDetails;
import com.senior.console.api.security.AuthGrantedAuthority;
import com.senior.console.api.security.LoginInfoService;
import com.senior.domain.bo.request.AccountCreateBoRequest;
import com.senior.domain.bo.request.AccountQueryBoRequest;
import com.senior.domain.bo.request.AccountUpdateBoRequest;
import com.senior.domain.bo.request.ChangePasswordBoRequest;
import com.senior.domain.bo.response.AccountQueryBoResponse;
import com.senior.domain.bo.response.DepartmentQueryBoResponse;
import com.senior.domain.bo.response.RoleQueryBoResponse;
import com.senior.service.AccountService;
import com.senior.service.DepartmentService;
import com.senior.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户账号表(Account) Controller
 *
 * @author senior
 * @since 2020-11-29 12:12:42
 */
@Api(tags = "用户账号表接口}")
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private static final String DEFAULT_PASSWORD = "123456";

    @Resource
    private AccountService service;
    @Resource
    private RoleService roleService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private LoginInfoService loginInfoService;
    @Resource
    private DepartmentService departmentService;

    @ApiOperation(value = "新增接口", notes = "根据接口参数新增一条数据")
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ACCOUNT_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody AccountCreateVoRequest request) {
        boolean success = createAccount(request);
        return success ? Result.ok(Boolean.TRUE) : Result.error("创建失败");
    }

    @ApiOperation(value = "注册接口", notes = "注册新用户")
    @PostMapping("/register")
    public Result<Boolean> register(@Valid @RequestBody AccountCreateVoRequest request) {
        boolean success = createAccount(request);
        return success ? Result.ok(Boolean.TRUE) : Result.error("注册失败");
    }

    private Boolean createAccount(AccountCreateVoRequest request) {
        PreconditionsKits.checkArgs(
                CollectionUtils.isEmpty(
                        service.queryList(AccountQueryBoRequest.builder().passport(request.getPassport()).build())),
                "passport不能重复");
        PreconditionsKits.checkArgs(
                CollectionUtils.isEmpty(
                        service.queryList(AccountQueryBoRequest.builder().phone(request.getPhone()).build())),
                "手机号不能重复");
        PreconditionsKits.checkArgs(
                CollectionUtils.isEmpty(
                        service.queryList(AccountQueryBoRequest.builder().email(request.getEmail()).build())),
                "邮箱不能重复");
        AccountCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, AccountCreateBoRequest.class);
        boRequest.setCreateTime(System.currentTimeMillis());
        boRequest.setUpdateTime(System.currentTimeMillis());
        // 设置默认密码
        if (StringUtils.isBlank(request.getPassword())) {
            boRequest.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        } else {
            boRequest.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        // 角色不存在则分配系统默认角色
        if (request.getRoleId() == null) {
            boRequest.setRoleId(roleService.getDefaultRole().getId());
        }
        if (request.getStatus() == null) {
            boRequest.setStatus(AccountStatus.ENABLED.getCode());
        }
        if (request.getSex() == null) {
            boRequest.setSex(0);
        }
        return service.create(boRequest);
    }

    @ApiOperation(value = "删除接口", notes = "根据id删除目标数据")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ACCOUNT_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        boolean success = service.deleteById(id);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "修改接口", notes = "根据id修改目标数据")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ACCOUNT_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody AccountUpdateVoRequest request) {
        boolean success = updateAccount(id, request);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "个人信息修改接口", notes = "修改个人信息")
    @PostMapping("/updateInfo")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ACCOUNT_PERSONAL.name())")
    public Result<Boolean> updateInfo(@Valid @RequestBody AccountUpdateVoRequest request) {
        boolean success = updateAccount(request.getId(), request);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    private boolean updateAccount(Long id, AccountUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        List<AccountQueryBoResponse> passportExists = service
                .queryList(AccountQueryBoRequest.builder().passport(request.getPassport()).build());
        // passport不存在或者是当前id对应的passport
        PreconditionsKits.checkArgs(CollectionUtils.isEmpty(passportExists)
                || passportExists.size() == 1 && passportExists.get(0).getId().equals(request.getId()),
                "passport不能重复");
        List<AccountQueryBoResponse> phoneExists = service
                .queryList(AccountQueryBoRequest.builder().phone(request.getPhone()).build());
        // 手机号不存在或者是当前id对应的手机号
        PreconditionsKits.checkArgs(CollectionUtils.isEmpty(phoneExists)
                || phoneExists.size() == 1 && phoneExists.get(0).getId().equals(request.getId()),
                "手机号不能重复");
        List<AccountQueryBoResponse> emailExists = service
                .queryList(AccountQueryBoRequest.builder().email(request.getEmail()).build());
        // 邮箱不存在或者是当前id对应的邮箱
        PreconditionsKits.checkArgs(CollectionUtils.isEmpty(emailExists)
                || emailExists.size() == 1 && emailExists.get(0).getId().equals(request.getId()),
                "邮箱不能重复");
        request.setId(id);
        AccountUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, AccountUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        return service.updateById(boRequest);
    }

    @ApiOperation(value = "单条查询接口", notes = "根据id查询一条目标数据")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ACCOUNT_QUERY.name())")
    public Result<AccountQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        AccountQueryBoResponse boResponse = service.getById(id);
        AccountQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse, AccountQueryVoResponse.class);
        if (voResponse != null) {
            if (voResponse.getRoleId() != null) {
                RoleQueryBoResponse role = roleService.getById(voResponse.getRoleId());
                voResponse.setRoleName(role.getName());
            }
            if (voResponse.getDepartmentId() != null) {
                DepartmentQueryBoResponse department = departmentService.getById(voResponse.getDepartmentId());
                voResponse.setDepartmentName(department.getName());
            }
        }
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ACCOUNT_QUERY.name())")
    public Result<Page<AccountQueryVoResponse>> pageQuery(AccountQueryVoRequest request) {
        AccountQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, AccountQueryBoRequest.class);
        Long count = service.queryCount(boRequest);
        if (count == null || count == 0) {
            return Result.ok(Page.empty());
        }
        List<AccountQueryBoResponse> responses = service.queryList(boRequest);
        List<AccountQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses,
                AccountQueryVoResponse.class);
        List<Long> roleIds = CollectionKits.nullToEmpty(responses).stream().map(AccountQueryBoResponse::getRoleId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Long> departmentIds = CollectionKits.nullToEmpty(responses).stream()
                .map(AccountQueryBoResponse::getDepartmentId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        ImmutableMap<Long, RoleQueryBoResponse> roleMap = Maps.uniqueIndex(roleService.getByIds(roleIds),
                RoleQueryBoResponse::getId);
        ImmutableMap<Long, DepartmentQueryBoResponse> departmentMap = Maps
                .uniqueIndex(departmentService.getByIds(departmentIds), DepartmentQueryBoResponse::getId);
        for (AccountQueryVoResponse account : voResponses) {
            account.setRoleName(Optional.ofNullable(roleMap.get(account.getRoleId())).map(RoleQueryBoResponse::getName)
                    .orElse(null));
            account.setDepartmentName(Optional.ofNullable(departmentMap.get(account.getDepartmentId()))
                    .map(DepartmentQueryBoResponse::getName).orElse(null));
        }
        return Result.ok(Page.fill(count, voResponses));
    }

    @ApiOperation("修改当前账号密码")
    @PostMapping("/changePassword")
    public Result<Void> changePassword(@RequestBody @Valid ChangePasswordVoRequest request) {
        AccountQueryBoResponse loginAccount = loginInfoService.getLoginAccount();
        PreconditionsKits.checkNotNull(loginAccount, "账号不存在");
        PreconditionsKits.checkArgs(passwordEncoder.matches(request.getOldPassword(), loginAccount.getPassword()),
                "旧密码错误");
        String encodePassword = passwordEncoder.encode(request.getNewPassword());

        service.changePassword(ChangePasswordBoRequest.builder().id(loginAccount.getId()).password(encodePassword)
                .updateTime(System.currentTimeMillis()).build());
        return Result.ok();
    }

    @ApiOperation("获取当前登录账号的信息")
    @RequestMapping(value = "/current", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<CurrentAccountVoResponse> current(HttpServletRequest request) {
        AccountUserDetails userDetails = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        AccountQueryBoResponse loginAccount = service.getById(userDetails.getAccount().getId());
        CurrentAccountVoResponse vo = BeanCopierKits.copyProperties(loginAccount,
                CurrentAccountVoResponse.class);
        vo.setAuthorities(CollectionKits.nullToEmpty(userDetails.getAuthorities()).stream()
                .map(AuthGrantedAuthority::getAuthority).collect(Collectors.toList()));
        RoleQueryBoResponse role = roleService.getById(loginAccount.getRoleId());
        vo.setRoleName(role.getName());
        return Result.ok(vo);
    }

}
