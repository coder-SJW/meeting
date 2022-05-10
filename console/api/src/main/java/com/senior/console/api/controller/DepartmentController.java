package com.senior.console.api.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
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

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.google.common.collect.Lists;
import com.senior.common.Page;
import com.senior.common.Result;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.console.api.controller.request.DepartmentCreateVoRequest;
import com.senior.console.api.controller.request.DepartmentQueryVoRequest;
import com.senior.console.api.controller.request.DepartmentUpdateVoRequest;
import com.senior.console.api.controller.response.DepartmentQueryVoResponse;
import com.senior.domain.bo.request.DepartmentCreateBoRequest;
import com.senior.domain.bo.request.DepartmentQueryBoRequest;
import com.senior.domain.bo.request.DepartmentUpdateBoRequest;
import com.senior.domain.bo.response.DepartmentQueryBoResponse;
import com.senior.service.DepartmentService;
import com.senior.service.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 部门(Department) Controller
 *
 * @author senior
 * @since 2021-08-17 11:21:22
 */
@Api(tags = "部门接口}")
@RestController
@Slf4j
@RequestMapping("/api/department")
public class DepartmentController extends AbstractController {

    @Resource
    private DepartmentService departmentService;
    @Resource
    private ImageService imageService;

    @ApiOperation(value = "新增接口", notes = "根据接口参数新增一条数据")
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).DEPARTMENT_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody DepartmentCreateVoRequest request) {
        long nameCount = departmentService
                .queryCount(DepartmentQueryBoRequest.builder().name(request.getName()).build());
        PreconditionsKits.checkArgs(nameCount == 0, "部门不能重复");
        DepartmentCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, DepartmentCreateBoRequest.class);
        boRequest.setCreateTime(System.currentTimeMillis());
        boolean success = departmentService.create(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("创建失败");
    }

    @ApiOperation(value = "删除接口", notes = "根据id删除目标数据")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).DEPARTMENT_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        boolean success = departmentService.deleteById(id);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "修改接口", notes = "根据id修改目标数据")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).DEPARTMENT_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody DepartmentUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        List<DepartmentQueryBoResponse> nameList = departmentService
                .queryList(DepartmentQueryBoRequest.builder().name(request.getName()).build());
        // 数据库为空或者就是本次修改的数据
        PreconditionsKits.checkArgs(
                CollectionUtils.isEmpty(nameList) || nameList.get(0).getId().equals(request.getId()),
                "部门不能重复");
        request.setId(id);
        DepartmentUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, DepartmentUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        boolean success = departmentService.updateById(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "单条查询接口", notes = "根据id查询一条目标数据")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).DEPARTMENT_QUERY.name())")
    public Result<DepartmentQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        DepartmentQueryBoResponse boResponse = departmentService.getById(id);
        DepartmentQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse,
                DepartmentQueryVoResponse.class);
        fillFields(Lists.newArrayList(voResponse));
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).DEPARTMENT_QUERY.name())")
    public Result<Page<DepartmentQueryVoResponse>> pageQuery(DepartmentQueryVoRequest request) {
        DepartmentQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, DepartmentQueryBoRequest.class);
        long count = departmentService.queryCount(boRequest);
        if (count == 0) {
            return Result.ok(Page.empty());
        }
        List<DepartmentQueryBoResponse> responses = departmentService.queryList(boRequest);
        List<DepartmentQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses,
                DepartmentQueryVoResponse.class);
        fillFields(voResponses);
        return Result.ok(Page.fill(count, voResponses));
    }

    @ApiOperation(value = "导出接口", notes = "根据条件导出目标数据")
    @GetMapping(value = "/exportExcel")
    public void export(HttpServletResponse servletResponse,
            DepartmentQueryVoRequest request) throws IOException {
        DepartmentQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, DepartmentQueryBoRequest.class);
        boRequest.setPageNum(1);
        // 最多导出10万的数据
        boRequest.setPageSize(100000);
        List<DepartmentQueryBoResponse> responses = departmentService.queryList(boRequest);
        List<DepartmentQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses,
                DepartmentQueryVoResponse.class);
        fillFields(voResponses);

        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("部门数据表", "UTF-8").replace("\\+", "%20");
        setExcelResponse(servletResponse, fileName);
        WriteCellStyle style = new WriteCellStyle();
        // 设置字体并水平居中
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        WriteFont font = new WriteFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        style.setWriteFont(font);

        EasyExcel.write(servletResponse.getOutputStream(), DepartmentQueryVoResponse.class)
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(25))
                .registerWriteHandler(new HorizontalCellStyleStrategy(new WriteCellStyle(), style))
                .sheet("数据")
                .doWrite(voResponses);

    }

    /**
     * 填充关联实体字段
     * 
     * @param voResponses
     */
    private void fillFields(List<DepartmentQueryVoResponse> voResponses) {

    }
}
