package com.senior.console.api.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.senior.common.Page;
import com.senior.common.Result;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.console.api.controller.request.RepairCreateVoRequest;
import com.senior.console.api.controller.request.RepairQueryVoRequest;
import com.senior.console.api.controller.request.RepairUpdateVoRequest;
import com.senior.console.api.controller.response.RepairQueryVoResponse;
import com.senior.domain.bo.request.RepairCreateBoRequest;
import com.senior.domain.bo.request.RepairQueryBoRequest;
import com.senior.domain.bo.request.RepairUpdateBoRequest;
import com.senior.domain.bo.response.AccountQueryBoResponse;
import com.senior.domain.bo.response.RepairQueryBoResponse;
import com.senior.domain.bo.response.RoomQueryBoResponse;
import com.senior.service.AccountService;
import com.senior.service.ImageService;
import com.senior.service.RepairService;
import com.senior.service.RoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 报修(Repair) Controller
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Api(tags = "报修接口}")
@RestController
@Slf4j
@RequestMapping("/api/repair")
public class RepairController extends AbstractController {

    @Resource
    private RepairService repairService;
    @Resource
    private ImageService imageService;

    @Resource
    private AccountService accountService;
    @Resource
    private RoomService roomService;

    @ApiOperation(value = "新增接口", notes = "根据接口参数新增一条数据")
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).REPAIR_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody RepairCreateVoRequest request) {
        RepairCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, RepairCreateBoRequest.class);
        boRequest.setStatus(1);
        boRequest.setCreateTime(System.currentTimeMillis());
        boolean success = repairService.create(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("创建失败");
    }

    @ApiOperation(value = "删除接口", notes = "根据id删除目标数据")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).REPAIR_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        boolean success = repairService.deleteById(id);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "修改接口", notes = "根据id修改目标数据")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).REPAIR_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody RepairUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        request.setId(id);
        RepairUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, RepairUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        boolean success = repairService.updateById(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "单条查询接口", notes = "根据id查询一条目标数据")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).REPAIR_QUERY.name())")
    public Result<RepairQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        RepairQueryBoResponse boResponse = repairService.getById(id);
        RepairQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse, RepairQueryVoResponse.class);
        fillFields(Lists.newArrayList(voResponse));
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).REPAIR_QUERY.name())")
    public Result<Page<RepairQueryVoResponse>> pageQuery(RepairQueryVoRequest request) {
        RepairQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, RepairQueryBoRequest.class);
        long count = repairService.queryCount(boRequest);
        if (count == 0) {
            return Result.ok(Page.empty());
        }
        List<RepairQueryBoResponse> responses = repairService.queryList(boRequest);
        List<RepairQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses, RepairQueryVoResponse.class);
        fillFields(voResponses);
        return Result.ok(Page.fill(count, voResponses));
    }

    @ApiOperation(value = "导出接口", notes = "根据条件导出目标数据")
    @GetMapping(value = "/exportExcel")
    public void export(HttpServletResponse servletResponse,
            RepairQueryVoRequest request) throws IOException {
        RepairQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, RepairQueryBoRequest.class);
        boRequest.setPageNum(1);
        // 最多导出10万的数据
        boRequest.setPageSize(100000);
        List<RepairQueryBoResponse> responses = repairService.queryList(boRequest);
        List<RepairQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses, RepairQueryVoResponse.class);
        fillFields(voResponses);

        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("报修数据表", "UTF-8").replace("\\+", "%20");
        setExcelResponse(servletResponse, fileName);
        WriteCellStyle style = new WriteCellStyle();
        // 设置字体并水平居中
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        WriteFont font = new WriteFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        style.setWriteFont(font);

        EasyExcel.write(servletResponse.getOutputStream(), RepairQueryVoResponse.class)
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
    private void fillFields(List<RepairQueryVoResponse> voResponses) {
        List<Long> accountIds = voResponses.stream().map(RepairQueryVoResponse::getAccountId)
                .collect(Collectors.toList());
        ImmutableMap<Long, AccountQueryBoResponse> accountMap = Maps.uniqueIndex(
                accountService.getByIds(accountIds),
                AccountQueryBoResponse::getId);

        for (RepairQueryVoResponse voResponse : voResponses) {
            AccountQueryBoResponse boResponse = accountMap.get(voResponse.getAccountId());
            if (boResponse != null) {
                voResponse.setAccountPassport(boResponse.getPassport());
            }
        }
        List<Long> roomIds = voResponses.stream().map(RepairQueryVoResponse::getRoomId)
                .collect(Collectors.toList());
        ImmutableMap<Long, RoomQueryBoResponse> roomMap = Maps.uniqueIndex(
                roomService.getByIds(roomIds),
                RoomQueryBoResponse::getId);

        for (RepairQueryVoResponse voResponse : voResponses) {
            RoomQueryBoResponse boResponse = roomMap.get(voResponse.getRoomId());
            if (boResponse != null) {
                voResponse.setRoomName(boResponse.getName());
            }
        }

    }
}
