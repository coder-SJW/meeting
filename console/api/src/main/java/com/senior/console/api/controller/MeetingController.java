package com.senior.console.api.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.senior.console.api.controller.request.MeetStatusDTO;
import com.senior.console.api.util.MailUtil;
import com.senior.service.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
import com.senior.console.api.controller.request.MeetingCreateVoRequest;
import com.senior.console.api.controller.request.MeetingQueryVoRequest;
import com.senior.console.api.controller.request.MeetingUpdateVoRequest;
import com.senior.console.api.controller.response.AccountQueryVoResponse;
import com.senior.console.api.controller.response.MeetingQueryVoResponse;
import com.senior.console.api.security.LoginInfoService;
import com.senior.domain.bo.request.MeetingCreateBoRequest;
import com.senior.domain.bo.request.MeetingQueryBoRequest;
import com.senior.domain.bo.request.MeetingUpdateBoRequest;
import com.senior.domain.bo.request.ReserveCreateBoRequest;
import com.senior.domain.bo.request.ReserveUpdateBoRequest;
import com.senior.domain.bo.response.AccountQueryBoResponse;
import com.senior.domain.bo.response.MeetingQueryBoResponse;
import com.senior.domain.bo.response.ReserveQueryBoResponse;
import com.senior.domain.bo.response.RoomQueryBoResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 会议室(Meeting) Controller
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Api(tags = "会议室接口}")
@RestController
@Slf4j
@RequestMapping("/api/meeting")
public class MeetingController extends AbstractController {

    @Resource
    private MeetingService meetingService;
    @Resource
    private ImageService imageService;

    @Resource
    private RoomService roomService;
    @Resource
    private ReserveService reserveService;
    @Resource
    private LoginInfoService loginInfoService;
    @Resource
    private AccountService accountService;
    @Resource
    private MailUtil mailUtil;

    @PostMapping("/review")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).MEETING_CREATE.name())")
    public Result<Boolean> review(@RequestBody MeetStatusDTO meetStatusDTO){
        MeetingQueryBoResponse meeting = meetingService.getById(meetStatusDTO.getId());
        MeetingUpdateBoRequest meetingUpdateDTO = BeanCopierKits.copyProperties(meeting, MeetingUpdateBoRequest.class);
        meetingUpdateDTO.setStatus(meetStatusDTO.getStatus());
        boolean res = meetingService.updateById(meetingUpdateDTO);
        return res ? Result.ok(Boolean.TRUE) : Result.error("审核失败");
    }


    @ApiOperation(value = "会议预约", notes = "根据接口参数新增一条数据")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).MEETING_CREATE.name())")
    public Result<Boolean> create(@Valid @RequestBody MeetingCreateVoRequest request) {
        MeetingCreateBoRequest boRequest = BeanCopierKits.copyProperties(request, MeetingCreateBoRequest.class);
        boRequest.setCreateTime(System.currentTimeMillis());
        boRequest.setStatus("0");
        // 新增会议
        boolean success = meetingService.create(boRequest);
        // 新增会议记录
        reserveService.create(
                ReserveCreateBoRequest.builder().roomId(request.getRoomId()).createTime(System.currentTimeMillis())
                        .reserveStartTime(request.getReserveStartTime()).reserveEndTime(request.getReserveEndTime())
                        .content(request.getName()).accountId(loginInfoService.getLoginAccount().getId())
                        .meetingId(boRequest.getId()).build());
        // 预约成功后邮件通知
        List<AccountQueryBoResponse> userList = accountService.getByIds(boRequest.getAccountIds());
        for (AccountQueryBoResponse user : userList) {
            mailUtil.sendTextMail(user.getEmail(), "会议通知", "您有新的会议通知，请注意查收");
        }
        return success ? Result.ok(Boolean.TRUE) : Result.error("创建失败");
    }

    @ApiOperation(value = "删除接口", notes = "根据id删除目标数据")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).MEETING_DELETE.name())")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        boolean success = meetingService.deleteById(id);
        ReserveQueryBoResponse reserve = reserveService.getByMeetingId(id);
        reserveService.deleteById(reserve.getId());
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "修改接口", notes = "根据id修改目标数据")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).MEETING_UPDATE.name())")
    public Result<Boolean> update(@PathVariable("id") Long id, @Valid @RequestBody MeetingUpdateVoRequest request) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        request.setId(id);
        MeetingUpdateBoRequest boRequest = BeanCopierKits.copyProperties(request, MeetingUpdateBoRequest.class);
        boRequest.setUpdateTime(System.currentTimeMillis());
        boolean success = meetingService.updateById(boRequest);
        if (request.getReserveEndTime() != null && request.getReserveStartTime() != null) {
            ReserveQueryBoResponse reserve = reserveService.getByMeetingId(id);
            reserveService.updateById(ReserveUpdateBoRequest.builder().roomId(request.getRoomId()).meetingId(id)
                    .reserveStartTime(request.getReserveStartTime()).reserveEndTime(request.getReserveEndTime())
                    .updateTime(System.currentTimeMillis()).id(reserve.getId()).build());
        }
        return success ? Result.ok(Boolean.TRUE) : Result.error("删除失败");
    }

    @ApiOperation(value = "单条查询接口", notes = "根据id查询一条目标数据")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).MEETING_QUERY.name())")
    public Result<MeetingQueryVoResponse> get(@PathVariable("id") Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        MeetingQueryBoResponse boResponse = meetingService.getById(id);
        MeetingQueryVoResponse voResponse = BeanCopierKits.copyProperties(boResponse, MeetingQueryVoResponse.class);
        fillFields(Lists.newArrayList(voResponse));
        return Result.ok(voResponse);
    }

    @ApiOperation(value = "分页查询接口", notes = "根据条件分页查询多条目标数据")
    @GetMapping("")
    @PreAuthorize("hasAuthority(T(com.senior.console.api.security.Authority).MEETING_QUERY.name())")
    public Result<Page<MeetingQueryVoResponse>> pageQuery(MeetingQueryVoRequest request) {
        MeetingQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, MeetingQueryBoRequest.class);
        long count = meetingService.queryCount(boRequest);
        if (count == 0) {
            return Result.ok(Page.empty());
        }
        List<MeetingQueryBoResponse> responses = meetingService.queryList(boRequest);
        List<MeetingQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses,
                MeetingQueryVoResponse.class);
        fillFields(voResponses);
        return Result.ok(Page.fill(count, voResponses));
    }

    @ApiOperation(value = "导出接口", notes = "根据条件导出目标数据")
    @GetMapping(value = "/exportExcel")
    public void export(HttpServletResponse servletResponse,
            MeetingQueryVoRequest request) throws IOException {
        MeetingQueryBoRequest boRequest = BeanCopierKits.copyProperties(request, MeetingQueryBoRequest.class);
        boRequest.setPageNum(1);
        // 最多导出10万的数据
        boRequest.setPageSize(100000);
        List<MeetingQueryBoResponse> responses = meetingService.queryList(boRequest);
        List<MeetingQueryVoResponse> voResponses = BeanCopierKits.copyProperties(responses,
                MeetingQueryVoResponse.class);
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

        EasyExcel.write(servletResponse.getOutputStream(), MeetingQueryVoResponse.class)
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
    private void fillFields(List<MeetingQueryVoResponse> voResponses) {
        List<Long> roomIds = voResponses.stream().map(MeetingQueryVoResponse::getRoomId)
                .collect(Collectors.toList());
        ImmutableMap<Long, RoomQueryBoResponse> roomMap = Maps.uniqueIndex(
                roomService.getByIds(roomIds),
                RoomQueryBoResponse::getId);
        Set<Long> accountIds = voResponses.stream().map(MeetingQueryVoResponse::getAccountIds).filter(Objects::nonNull)
                .flatMap(List::stream).collect(Collectors.toSet());
        List<AccountQueryBoResponse> accountList = accountService.getByIds(new ArrayList<>(accountIds));
        ImmutableMap<Long, AccountQueryBoResponse> accountMap = Maps.uniqueIndex(accountList,
                AccountQueryBoResponse::getId);
        for (MeetingQueryVoResponse voResponse : voResponses) {
            RoomQueryBoResponse boResponse = roomMap.get(voResponse.getRoomId());
            if (boResponse != null) {
                voResponse.setRoomName(boResponse.getName());
            }

            if (!CollectionUtils.isEmpty(voResponse.getAccountIds())) {
                List<AccountQueryBoResponse> list = Lists.newArrayList();
                for (Long accountId : voResponse.getAccountIds()) {
                    list.add(accountMap.get(accountId));
                }
                voResponse.setAccounts(BeanCopierKits.copyProperties(list, AccountQueryVoResponse.class));
                voResponse.setAccountNames(
                        list.stream().map(AccountQueryBoResponse::getName).collect(Collectors.joining(",")));
            }
        }

    }
}
