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
 * ??????(Image) Controller
 *
 * @author senior
 */
@Api(tags = "????????????}")
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
            PreconditionsKits.checkArgs(StringUtils.containsIgnoreCase(contentType, "image"), "?????????????????????");
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
            log.error("??????????????????", e);
            return Result.error("??????????????????");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @ApiOperation(value = "????????????", notes = "????????????????????????????????????")
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).IMAGE_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody ImageCreateVoRequest request) {
        ImageCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, ImageCreateBoRequest.class);
        boRequest.setCreateTime(System.currentTimeMillis());
        boolean success = imageService.create(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("????????????");
    }

    @ApiOperation(value = "????????????", notes = "??????id??????????????????")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).IMAGE_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        boolean success = imageService.deleteById(id);
        return success ? Result.ok(Boolean.TRUE) : Result.error("????????????");
    }

    @ApiOperation(value = "????????????", notes = "??????id??????????????????")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).IMAGE_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody ImageUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id????????????");
        request.setId(id);
        ImageUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, ImageUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        boolean success = imageService.updateById(boRequest);
        return success ? Result.ok(Boolean.TRUE) : Result.error("????????????");
    }

    @ApiOperation(value = "??????????????????", notes = "??????id????????????????????????")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).IMAGE_QUERY.name())")
    public Result<ImageQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id????????????");
        ImageQueryBoResponse boResponse = imageService.getById(id);
        ImageQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse, ImageQueryVoResponse.class);
        fillFields(Lists.newArrayList(voResponse));
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "??????????????????", notes = "??????????????????????????????????????????")
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

    @ApiOperation(value = "????????????", notes = "??????????????????????????????")
    @GetMapping(value = "/exportExcel")
    public void export(HttpServletResponse servletResponse,
            ImageQueryVoRequest request) throws IOException {
        ImageQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, ImageQueryBoRequest.class);
        boRequest.setPageNum(1);
        // ????????????10????????????
        boRequest.setPageSize(100000);
        List<ImageQueryBoResponse> responses = imageService.queryList(boRequest);
        List<ImageQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses, ImageQueryVoResponse.class);
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

        EasyExcel.write(servletResponse.getOutputStream(), ImageQueryVoResponse.class)
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
    private void fillFields(List<ImageQueryVoResponse> voResponses) {
    }
}
