package com.senior.console.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import com.senior.console.api.controller.request.ImageCreateVoRequest;
import com.senior.console.api.controller.request.ImageQueryVoRequest;
import com.senior.console.api.controller.request.ImageUpdateVoRequest;
import com.senior.console.api.controller.response.ImageQueryVoResponse;
import com.senior.domain.bo.request.ImageCreateBoRequest;
import com.senior.domain.bo.request.ImageQueryBoRequest;
import com.senior.domain.bo.request.ImageUpdateBoRequest;
import com.senior.domain.bo.response.ImageQueryBoResponse;
import com.senior.service.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 图片(Image) Controller
 *
 * @author senior
 */
@Api(tags = "图片接口}")
@RestController
@Slf4j
@RequestMapping("/api/image")
public class ImageController extends AbstractController {

    @Resource
    private ImageService imageService;

    @RequestMapping(value = "/upload")
    public Result<Long> upload(@RequestParam MultipartFile file, HttpServletRequest request) {
        InputStream in = null;
        String format = "data:%s;base64,";
        try {
            request.setCharacterEncoding("utf-8");
            byte[] data = new byte[file.getInputStream().available()];
            String name = file.getOriginalFilename();
            in = file.getInputStream();
            in.read(data);
            String contentType = file.getContentType();
            PreconditionsKits.checkArgs(StringUtils.containsIgnoreCase(contentType, "image"), "只允许上传图片");
            String prefix = String.format(format, contentType);
            String imageData = Base64.getEncoder().encodeToString(data);
            ImageCreateBoRequest image = ImageCreateBoRequest.builder().createTime(System.currentTimeMillis())
                    .name(name)
                    .data(prefix + imageData).build();
            image.setCreateTime(System.currentTimeMillis());
            image.setName(name);
            image.setData(prefix + imageData);
            imageService.create(image);
            return Result.ok(image.getId());
        } catch (Exception e) {
            log.error("图片上传失败", e);
            return Result.error("图片上传失败");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @ApiOperation(value = "新增接口", notes = "根据接口参数新增一条数据")
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).IMAGE_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody ImageCreateVoRequest request) {
        ImageCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, ImageCreateBoRequest.class);
        boRequest.setCreateTime(System.currentTimeMillis());
        boolean success = imageService.create(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("创建失败");
    }

    @ApiOperation(value = "删除接口", notes = "根据id删除目标数据")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).IMAGE_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        boolean success = imageService.deleteById(id);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "修改接口", notes = "根据id修改目标数据")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).IMAGE_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody ImageUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        request.setId(id);
        ImageUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, ImageUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        boolean success = imageService.updateById(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "单条查询接口", notes = "根据id查询一条目标数据")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).IMAGE_QUERY.name())")
    public Result<ImageQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        ImageQueryBoResponse boResponse = imageService.getById(id);
        ImageQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse, ImageQueryVoResponse.class);
        fillFields(Lists.newArrayList(voResponse));
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).IMAGE_QUERY.name())")
    public Result<Page<ImageQueryVoResponse>> pageQuery(ImageQueryVoRequest request) {
        ImageQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, ImageQueryBoRequest.class);
        long count = imageService.queryCount(boRequest);
        if (count == 0) {
            return Result.ok(Page.empty());
        }
        List<ImageQueryBoResponse> responses = imageService.queryList(boRequest);
        List<ImageQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses, ImageQueryVoResponse.class);
        fillFields(voResponses);
        return Result.ok(Page.fill(count, voResponses));
    }

    @ApiOperation(value = "导出接口", notes = "根据条件导出目标数据")
    @GetMapping(value = "/exportExcel")
    public void export(HttpServletResponse servletResponse,
            ImageQueryVoRequest request) throws IOException {
        ImageQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, ImageQueryBoRequest.class);
        boRequest.setPageNum(1);
        // 最多导出10万的数据
        boRequest.setPageSize(100000);
        List<ImageQueryBoResponse> responses = imageService.queryList(boRequest);
        List<ImageQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses, ImageQueryVoResponse.class);
        fillFields(voResponses);

        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("图片数据表", "UTF-8").replace("\\+", "%20");
        setExcelResponse(servletResponse, fileName);
        WriteCellStyle style = new WriteCellStyle();
        // 设置字体并水平居中
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        WriteFont font = new WriteFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        style.setWriteFont(font);

        EasyExcel.write(servletResponse.getOutputStream(), ImageQueryVoResponse.class)
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
    private void fillFields(List<ImageQueryVoResponse> voResponses) {
    }
}
