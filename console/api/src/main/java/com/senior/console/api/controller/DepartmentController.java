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
 * ??????(Department) Controller
 *
 * @author senior
 * @since 2021-08-17 11:21:22
 */
@Api(tags = "????????????}")
@RestController
@Slf4j
@RequestMapping("/api/department")
public class DepartmentController extends AbstractController {

    @Resource
    private DepartmentService departmentService;
    @Resource
    private ImageService imageService;

    @ApiOperation(value = "????????????", notes = "????????????????????????????????????")
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).DEPARTMENT_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody DepartmentCreateVoRequest request) {
        long nameCount = departmentService
                .queryCount(DepartmentQueryBoRequest.builder().name(request.getName()).build());
        PreconditionsKits.checkArgs(nameCount == 0, "??????????????????");
        DepartmentCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, DepartmentCreateBoRequest.class);
        boRequest.setCreateTime(System.currentTimeMillis());
        boolean success = departmentService.create(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("????????????");
    }

    @ApiOperation(value = "????????????", notes = "??????id??????????????????")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).DEPARTMENT_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        boolean success = departmentService.deleteById(id);
        return success ? Result.ok(Boolean.TRUE) : Result.error("????????????");
    }

    @ApiOperation(value = "????????????", notes = "??????id??????????????????")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).DEPARTMENT_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody DepartmentUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id????????????");
        List<DepartmentQueryBoResponse> nameList = departmentService
                .queryList(DepartmentQueryBoRequest.builder().name(request.getName()).build());
        // ????????????????????????????????????????????????
        PreconditionsKits.checkArgs(
                CollectionUtils.isEmpty(nameList) || nameList.get(0).getId().equals(request.getId()),
                "??????????????????");
        request.setId(id);
        DepartmentUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, DepartmentUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        boolean success = departmentService.updateById(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("????????????");
    }

    @ApiOperation(value = "??????????????????", notes = "??????id????????????????????????")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).DEPARTMENT_QUERY.name())")
    public Result<DepartmentQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id????????????");
        DepartmentQueryBoResponse boResponse = departmentService.getById(id);
        DepartmentQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse,
                DepartmentQueryVoResponse.class);
        fillFields(Lists.newArrayList(voResponse));
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "??????????????????", notes = "??????????????????????????????????????????")
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

    @ApiOperation(value = "????????????", notes = "??????????????????????????????")
    @GetMapping(value = "/exportExcel")
    public void export(HttpServletResponse servletResponse,
            DepartmentQueryVoRequest request) throws IOException {
        DepartmentQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, DepartmentQueryBoRequest.class);
        boRequest.setPageNum(1);
        // ????????????10????????????
        boRequest.setPageSize(100000);
        List<DepartmentQueryBoResponse> responses = departmentService.queryList(boRequest);
        List<DepartmentQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses,
                DepartmentQueryVoResponse.class);
        fillFields(voResponses);

        // ??????URLEncoder.encode????????????????????????
        String fileName = URLEncoder.encode("???????????????", "UTF-8").replace("\\+", "%20");
        setExcelResponse(servletResponse, fileName);
        WriteCellStyle style = new WriteCellStyle();
        // ???????????????????????????
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        WriteFont font = new WriteFont();
        font.setFontName("??????");
        font.setFontHeightInPoints((short) 14);
        style.setWriteFont(font);

        EasyExcel.write(servletResponse.getOutputStream(), DepartmentQueryVoResponse.class)
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(25))
                .registerWriteHandler(new HorizontalCellStyleStrategy(new WriteCellStyle(), style))
                .sheet("??????")
                .doWrite(voResponses);

    }

    /**
     * ????????????????????????
     * 
     * @param voResponses
     */
    private void fillFields(List<DepartmentQueryVoResponse> voResponses) {

    }
}
