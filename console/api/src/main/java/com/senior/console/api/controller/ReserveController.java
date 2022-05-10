package com.senior.console.api.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.senior.common.Page;
import com.senior.common.Result;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.DateTimeKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.console.api.controller.request.ReserveCreateVoRequest;
import com.senior.console.api.controller.request.ReserveQueryVoRequest;
import com.senior.console.api.controller.request.ReserveUpdateVoRequest;
import com.senior.console.api.controller.response.ReserveEventVoResponse;
import com.senior.console.api.controller.response.ReserveQueryVoResponse;
import com.senior.console.api.security.LoginInfoService;
import com.senior.domain.bo.request.ReserveCreateBoRequest;
import com.senior.domain.bo.request.ReserveQueryBoRequest;
import com.senior.domain.bo.request.ReserveUpdateBoRequest;
import com.senior.domain.bo.response.AccountQueryBoResponse;
import com.senior.domain.bo.response.ReserveQueryBoResponse;
import com.senior.domain.bo.response.RoomQueryBoResponse;
import com.senior.service.AccountService;
import com.senior.service.ImageService;
import com.senior.service.ReserveService;
import com.senior.service.RoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 预约记录(Reserve) Controller
 *
 * @author sjw
 */
@Api(tags = "预约记录接口}")
@RestController
@Slf4j
@RequestMapping("/api/reserve")
public class ReserveController extends AbstractController {
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final Map<Long, String> colorMap = Maps.newHashMap();
    private static final List<String> colors = Lists.newArrayList("#CCCC99", "#FFCCCC", "#FFFF99", "#CCCCFF", "#0099CC",
            "#FF6666", "#FF6666", "#FF9966", "#FF6666", "#FFCCCC", "#CC9966", "#99CC66");

    static {
        for (int i = 0; i < colors.size(); i++) {
            colorMap.put((long) i, colors.get(i));
        }
    }

    @Resource
    private ReserveService reserveService;
    @Resource
    private ImageService imageService;

    @Resource
    private AccountService accountService;
    @Resource
    private RoomService roomService;
    @Resource
    private LoginInfoService loginInfoService;

    @ApiOperation(value = "新增接口", notes = "根据接口参数新增一条数据")
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).RESERVE_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody ReserveCreateVoRequest request) {
        ReserveCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, ReserveCreateBoRequest.class);
        boRequest.setCreateTime(System.currentTimeMillis());
        boolean success = reserveService.create(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("创建失败");
    }

    @ApiOperation(value = "删除接口", notes = "根据id删除目标数据")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).RESERVE_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        boolean success = reserveService.deleteById(id);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "修改接口", notes = "根据id修改目标数据")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).RESERVE_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody ReserveUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        request.setId(id);
        ReserveUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, ReserveUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        boolean success = reserveService.updateById(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "单条查询接口", notes = "根据id查询一条目标数据")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).RESERVE_QUERY.name())")
    public Result<ReserveQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        ReserveQueryBoResponse boResponse = reserveService.getById(id);
        ReserveQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse, ReserveQueryVoResponse.class);
        fillFields(Lists.newArrayList(voResponse));
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).RESERVE_QUERY.name())")
    public Result<Page<ReserveQueryVoResponse>> pageQuery(ReserveQueryVoRequest request) {
        ReserveQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, ReserveQueryBoRequest.class);
        long count = reserveService.queryCount(boRequest);
        if (count == 0) {
            return Result.ok(Page.empty());
        }
        List<ReserveQueryBoResponse> responses = reserveService.queryList(boRequest);
        List<ReserveQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses,
                ReserveQueryVoResponse.class);
        fillFields(voResponses);
        return Result.ok(Page.fill(count, voResponses));
    }

    @ApiOperation(value = "查询事件接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("/events")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).RESERVE_QUERY.name())")
    public Result<Page<ReserveEventVoResponse>> events(ReserveQueryVoRequest request) {
        Result<Page<ReserveQueryVoResponse>> result = pageQuery(request);
        if (CollectionUtils.isEmpty(result.getData().getRows())) {
            return Result.ok(Page.empty());
        }
        List<ReserveEventVoResponse> events = Lists.newArrayList();
        for (ReserveQueryVoResponse row : result.getData().getRows()) {
            boolean editable = row.getAccountId().equals(loginInfoService.getLoginAccount().getId());
            ReserveEventVoResponse event = ReserveEventVoResponse.builder()
                    .title(row.getContent())
                    .start(DateTimeKits.format(row.getReserveStartTime(), TIME_FORMAT))
                    .end(DateTimeKits.format(row.getReserveEndTime(), TIME_FORMAT))
                    .resourceIds(Lists.newArrayList(row.getRoomId()))
                    .editable(editable)
                    .color(colorMap.get(row.getId() % colors.size()))
                    .textColor("black")
                    .overlap(false)
                    .displayEventTime(true)
                    .eventMaxStack(6)
                    .build();
            events.add(event);
        }
        return Result.ok(Page.fill(result.getData().getCount(), events));
    }

    @ApiOperation(value = "查询我的会议接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("/myEvents")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).RESERVE_QUERY.name())")
    public Result<Page<ReserveEventVoResponse>> myEvents(ReserveQueryVoRequest request) {
        request.setAccountId(loginInfoService.getLoginAccount().getId());
        List<ReserveQueryBoResponse> list = reserveService
                .queryMyEvents(BeanCopierKits.copyProperties(request, ReserveQueryBoRequest.class));
        if (CollectionUtils.isEmpty(list)) {
            return Result.ok(Page.empty());
        }
        List<ReserveEventVoResponse> events = Lists.newArrayList();
        for (ReserveQueryBoResponse row : list) {
            ReserveEventVoResponse event = ReserveEventVoResponse.builder()
                    .title(row.getRoomName()+" "+row.getContent())
                    .start(DateTimeKits.format(row.getReserveStartTime(), TIME_FORMAT))
                    .end(DateTimeKits.format(row.getReserveEndTime(), TIME_FORMAT))
                    .resourceIds(Lists.newArrayList(row.getRoomId()))
                    .editable(false)
                    .color(colorMap.get(row.getId() % colors.size()))
                    .textColor("black")
                    .overlap(false)
                    .displayEventTime(false)
                    .eventMaxStack(6)
                    .build();
            events.add(event);
        }
        return Result.ok(Page.fill(list.size(), events));
    }

    @ApiOperation(value = "导出接口", notes = "根据条件导出目标数据")
    @GetMapping(value = "/exportExcel")
    public void export(HttpServletResponse servletResponse,
            ReserveQueryVoRequest request) throws IOException {
        ReserveQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, ReserveQueryBoRequest.class);
        boRequest.setPageNum(1);
        // 最多导出10万的数据
        boRequest.setPageSize(100000);
        List<ReserveQueryBoResponse> responses = reserveService.queryList(boRequest);
        List<ReserveQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses,
                ReserveQueryVoResponse.class);
        fillFields(voResponses);

        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("预约记录数据表", "UTF-8").replace("\\+", "%20");
        setExcelResponse(servletResponse, fileName);
        WriteCellStyle style = new WriteCellStyle();
        // 设置字体并水平居中
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        WriteFont font = new WriteFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        style.setWriteFont(font);

        EasyExcel.write(servletResponse.getOutputStream(), ReserveQueryVoResponse.class)
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
    private void fillFields(List<ReserveQueryVoResponse> voResponses) {
        List<Long> accountIds = voResponses.stream().map(ReserveQueryVoResponse::getAccountId)
                .collect(Collectors.toList());
        ImmutableMap<Long, AccountQueryBoResponse> accountMap = Maps.uniqueIndex(
                accountService.getByIds(accountIds),
                AccountQueryBoResponse::getId);

        for (ReserveQueryVoResponse voResponse : voResponses) {
            AccountQueryBoResponse boResponse = accountMap.get(voResponse.getAccountId());
            if (boResponse != null) {
                voResponse.setAccountPassport(boResponse.getPassport());
            }
        }
        List<Long> roomIds = voResponses.stream().map(ReserveQueryVoResponse::getRoomId)
                .collect(Collectors.toList());
        ImmutableMap<Long, RoomQueryBoResponse> roomMap = Maps.uniqueIndex(
                roomService.getByIds(roomIds),
                RoomQueryBoResponse::getId);

        for (ReserveQueryVoResponse voResponse : voResponses) {
            RoomQueryBoResponse boResponse = roomMap.get(voResponse.getRoomId());
            if (boResponse != null) {
                voResponse.setRoomName(boResponse.getName());
            }
        }
    }
}
