package com.senior.console.api.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
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
import com.senior.common.kits.PreconditionsKits;
import com.senior.console.api.controller.request.RoomCreateVoRequest;
import com.senior.console.api.controller.request.RoomQueryVoRequest;
import com.senior.console.api.controller.request.RoomUpdateVoRequest;
import com.senior.console.api.controller.response.RoomQueryVoResponse;
import com.senior.console.api.controller.response.RoomResourceVoResponse;
import com.senior.domain.bo.request.RoomCreateBoRequest;
import com.senior.domain.bo.request.RoomQueryBoRequest;
import com.senior.domain.bo.request.RoomUpdateBoRequest;
import com.senior.domain.bo.response.ImageBo;
import com.senior.domain.bo.response.RoomQueryBoResponse;
import com.senior.service.ImageService;
import com.senior.service.RoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 会议室(Room) Controller
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Api(tags = "会议室接口}")
@RestController
@Slf4j
@RequestMapping("/api/room")
public class RoomController extends AbstractController {

    @Resource
    private RoomService roomService;
    @Resource
    private ImageService imageService;

    @ApiOperation(value = "新增接口", notes = "根据接口参数新增一条数据")
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROOM_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody RoomCreateVoRequest request) {
        long nameCount = roomService.queryCount(RoomQueryBoRequest.builder().name(request.getName()).build());
        PreconditionsKits.checkArgs(nameCount == 0, "会议室不能重复");
        RoomCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, RoomCreateBoRequest.class);
        boRequest.setCreateTime(System.currentTimeMillis());
        boolean success = roomService.create(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("创建失败");
    }

    @ApiOperation(value = "删除接口", notes = "根据id删除目标数据")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROOM_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        boolean success = roomService.deleteById(id);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "修改接口", notes = "根据id修改目标数据")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROOM_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody RoomUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        List<RoomQueryBoResponse> nameList = roomService
                .queryList(RoomQueryBoRequest.builder().name(request.getName()).build());
        // 数据库为空或者就是本次修改的数据
        PreconditionsKits.checkArgs(
                CollectionUtils.isEmpty(nameList) || nameList.get(0).getId().equals(request.getId()),
                "会议室不能重复");
        request.setId(id);
        RoomUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, RoomUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        boolean success = roomService.updateById(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "单条查询接口", notes = "根据id查询一条目标数据")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROOM_QUERY.name())")
    public Result<RoomQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        RoomQueryBoResponse boResponse = roomService.getById(id);
        RoomQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse, RoomQueryVoResponse.class);
        fillFields(Lists.newArrayList(voResponse));
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROOM_QUERY.name())")
    public Result<Page<RoomQueryVoResponse>> pageQuery(RoomQueryVoRequest request) {
        RoomQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, RoomQueryBoRequest.class);
        long count = roomService.queryCount(boRequest);
        if (count == 0) {
            return Result.ok(Page.empty());
        }
        List<RoomQueryBoResponse> responses = roomService.queryList(boRequest);
        List<RoomQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses, RoomQueryVoResponse.class);
        fillFields(voResponses);
        return Result.ok(Page.fill(count, voResponses));
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("/resource")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).ROOM_QUERY.name())")
    public Result<Page<RoomResourceVoResponse>> resource(RoomQueryVoRequest request) {
        Result<Page<RoomQueryVoResponse>> result = pageQuery(request);
        if (CollectionUtils.isEmpty(result.getData().getRows())) {
            return Result.ok(Page.empty());
        }
        List<RoomResourceVoResponse> resources = Lists.newArrayList();
        for (RoomQueryVoResponse row : result.getData().getRows()) {
            RoomResourceVoResponse response = RoomResourceVoResponse.builder()
                    .id(row.getId())
                    .title(row.getName())
                    .businessHours(new RoomResourceVoResponse.BusinessHours("8:30", "20:30",
                            Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7)))
                    .build();
            resources.add(response);
        }
        return Result.ok(Page.fill(result.getData().getCount(), resources));
    }

    @ApiOperation(value = "导出接口", notes = "根据条件导出目标数据")
    @GetMapping(value = "/exportExcel")
    public void export(HttpServletResponse servletResponse,
            RoomQueryVoRequest request) throws IOException {
        RoomQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, RoomQueryBoRequest.class);
        boRequest.setPageNum(1);
        // 最多导出10万的数据
        boRequest.setPageSize(100000);
        List<RoomQueryBoResponse> responses = roomService.queryList(boRequest);
        List<RoomQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses, RoomQueryVoResponse.class);
        fillFields(voResponses);

        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("会议室数据表", "UTF-8").replace("\\+", "%20");
        setExcelResponse(servletResponse, fileName);
        WriteCellStyle style = new WriteCellStyle();
        // 设置字体并水平居中
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        WriteFont font = new WriteFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        style.setWriteFont(font);

        EasyExcel.write(servletResponse.getOutputStream(), RoomQueryVoResponse.class)
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
    private void fillFields(List<RoomQueryVoResponse> voResponses) {

        List<Long> imageIds = voResponses.stream().map(RoomQueryVoResponse::getRoomImageIds).filter(Objects::nonNull)
                .flatMap(List::stream).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(imageIds)) {
            List<ImageBo> imageBos = imageService.queryImages(imageIds);
            ImmutableMap<Long, ImageBo> map = Maps.uniqueIndex(imageBos, ImageBo::getImageId);
            for (RoomQueryVoResponse vo : voResponses) {
                if (CollectionUtils.isEmpty(vo.getRoomImageIds())) {
                    continue;
                }
                List<ImageBo> imageList = Lists.newArrayList();
                vo.getRoomImageIds().forEach(o -> imageList.add(map.get(o)));
                vo.setImageList(imageList);
            }
        }
    }
}
