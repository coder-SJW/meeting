package com.senior.console.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.LogAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.senior.common.Result;
import com.senior.common.enums.GroupType;
import com.senior.common.kits.CollectionKits;
import com.senior.common.kits.DateTimeKits;
import com.senior.console.api.controller.response.AccountGridVoResponse;
import com.senior.console.api.controller.response.DataGridVoResponse;
import com.senior.domain.bo.request.AccountQueryBoRequest;
import com.senior.domain.bo.request.MeetingQueryBoRequest;
import com.senior.domain.bo.request.RepairQueryBoRequest;
import com.senior.domain.bo.request.ReserveQueryBoRequest;
import com.senior.domain.bo.request.RoomQueryBoRequest;
import com.senior.domain.bo.response.MeetingQueryBoResponse;
import com.senior.domain.bo.response.RepairQueryBoResponse;
import com.senior.domain.bo.response.ReserveQueryBoResponse;
import com.senior.domain.bo.response.RoomQueryBoResponse;
import com.senior.service.AccountService;
import com.senior.service.DashboardService;
import com.senior.service.MeetingService;
import com.senior.service.RepairService;
import com.senior.service.ReserveService;
import com.senior.service.RoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * ?????????(Room) Controller
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Api(tags = "??????????????????}")
@RestController
@Slf4j
@RequestMapping("/api/statistics")
public class DashboardController extends AbstractController {

    private static final int MONTH_INTERVAL = 12;
    private static final int DAY_INTERVAL = 30;
    private static final int DAY_OF_WEEK_INTERVAL = 7;

    @Resource
    private DashboardService dashboardService;
    @Resource
    private AccountService accountService;
    @Resource
    private MeetingService meetingService;
    @Resource
    private RepairService repairService;
    @Resource
    private ReserveService reserveService;
    @Resource
    private RoomService roomService;

    @GetMapping("/accountGrid")
    @ApiOperation(value = "????????????????????????")
    public Result<AccountGridVoResponse> accountGrid() {
        long millis = System.currentTimeMillis();
        Long totalAccount = accountService.queryCount(new AccountQueryBoRequest());
        Long todayVisitors = accountService.queryCount(AccountQueryBoRequest.builder()
                .startLastLoginTime(DateTimeKits.getStartTimeOfDay(millis)).endLastLoginTime(millis).build());
        long targetDayBeginTime = getTargetDayBeginTime(DAY_OF_WEEK_INTERVAL);
        Long newAccount = accountService.queryCount(AccountQueryBoRequest.builder()
                .startCreateTime(DateTimeKits.getStartTimeOfDay(targetDayBeginTime)).endCreateTime(millis).build());
        return Result
                .ok(AccountGridVoResponse.builder().newAccountInLastWeek(newAccount).todayVisitors(todayVisitors)
                        .totalAccount(totalAccount)
                        .build());
    }

    @GetMapping("/dataGrid")
    @ApiOperation(value = "????????????????????????")
    public Result<DataGridVoResponse> dataGrid() {
        Option hourlyOption = getHourlyMeeting();
        Option dailyOption = getDailyMeeting();
        Option weeklyOption = getWeeklyMeeting();
        Option monthlyOption = getMonthlyMeeting();
        return Result
                .ok(DataGridVoResponse.builder().hourlyOption(hourlyOption).dailyOption(dailyOption)
                        .weeklyOption(weeklyOption).monthlyOption(monthlyOption)
                        .build());
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????4??????????????????????????????
     *
     * @return
     */
    private Option getHourlyMeeting() {
        List<String> days = getDays(DAY_OF_WEEK_INTERVAL);
        Map<String, List<Integer>> hourMap = Maps.newLinkedHashMap();
        HOUR_GROUP.forEach(h -> hourMap.put(h, new ArrayList<>()));
        for (String day : days) {
            long startTimeOfDay = DateTimeKits.getStartTimeOfDay(DateTimeKits.parse(day, YYYY_MM_DD));
            long endTimeOfDay = DateTimeKits.getEndTimeOfDay(DateTimeKits.parse(day, YYYY_MM_DD));
            List<MeetingQueryBoResponse> list = meetingService.groupBy(MeetingQueryBoRequest.builder()
                    .groupType(GroupType.HOURS).startCreateTime(startTimeOfDay).endCreateTime(endTimeOfDay).build());
            ImmutableListMultimap<String, MeetingQueryBoResponse> dateMap = Multimaps.index(list,
                    o -> TIME_MAP.get(o.getDate()));
            for (String hour : HOUR_GROUP) {
                // ???4???????????????????????????????????????????????????
                ImmutableList<MeetingQueryBoResponse> records = dateMap.get(hour);
                // ?????????????????????????????????
                int sum = CollectionKits.nullToEmpty(records).stream().mapToInt(MeetingQueryBoResponse::getCount)
                        .sum();
                hourMap.get(hour).add(sum);
            }
        }

        List<Bar> bars = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : hourMap.entrySet()) {
            Bar bar = new Bar(entry.getKey());
            bar.stack("?????????");
            bar.itemStyle().normal().label().show(true).position("insideRight");
            bar.data(entry.getValue().toArray());
            bars.add(bar);
        }

        Option option = new Option();
        option.title().text("??????7????????????????????????????????????").left(X.center).top("7%");
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend(HOUR_GROUP.toArray());
        option.toolbox().show(true).feature(Tool.saveAsImage);
        option.calculable(true);
        option.yAxis(new CategoryAxis().data(days.toArray()));
        option.xAxis(new ValueAxis());
        option.grid().left("15%").right("1%");
        option.series(bars.toArray(new Bar[0]));
        return option;
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    private Option getWeeklyMeeting() {
        List<String> days = getDays(DAY_OF_WEEK_INTERVAL);
        List<MeetingQueryBoResponse> list = meetingService.groupBy(MeetingQueryBoRequest.builder()
                .groupType(GroupType.DAY).startCreateTime(getTargetDayBeginTime(DAY_OF_WEEK_INTERVAL))
                .endCreateTime(System.currentTimeMillis()).build());
        ImmutableMap<String, MeetingQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                MeetingQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + DAY_OF_WEEK_INTERVAL + "???????????????").x(X.center);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
        option.legend().orient(Orient.vertical).left(X.left).data(days.toArray());
        Pie pie = new Pie("????????????");
        pie.radius("30%");
        pie.center("40%", "45%");
        List<Map<String, Object>> data = new ArrayList<>();
        for (String day : days) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", day);
            map.put("value", Optional.ofNullable(dateMap.get(day)).map(MeetingQueryBoResponse::getCount).orElse(0));
            data.add(map);
        }
        pie.data(data.toArray());
        option.series(pie);
        return option;
    }

    /**
     * ??????30??????????????????????????????
     *
     * @return
     */
    private Option getDailyMeeting() {
        List<String> days = getDays(DAY_INTERVAL);
        List<MeetingQueryBoResponse> list = meetingService.groupBy(MeetingQueryBoRequest.builder()
                .groupType(GroupType.DAY).startCreateTime(getTargetDayBeginTime(DAY_INTERVAL))
                .endCreateTime(System.currentTimeMillis()).build());
        ImmutableMap<String, MeetingQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                MeetingQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + DAY_INTERVAL + "?????????????????????").x(X.center);
        option.legend().x(X.left).data("?????????");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar),
                Tool.restore, Tool.saveAsImage);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}");
        option.calculable(true);
        option.yAxis(new LogAxis());
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.name("x").splitLine().show(false);
        categoryAxis.data(days.toArray());
        option.xAxis(categoryAxis);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} ??C");
        option.xAxis(valueAxis);
        Line line = new Line("?????????");
        line.data(days.stream().map(
                day -> Optional.ofNullable(dateMap.get(day)).map(MeetingQueryBoResponse::getCount).orElse(0))
                .toArray());
        option.series(line);
        return option;
    }

    /**
     * ??????12?????????????????????????????????
     *
     * @return
     */
    private Option getMonthlyMeeting() {
        List<String> months = getMonths(12);
        List<MeetingQueryBoResponse> list = meetingService.groupBy(MeetingQueryBoRequest.builder()
                .groupType(GroupType.MONTH).startCreateTime(getTargetMonthBeginTime(MONTH_INTERVAL))
                .endCreateTime(System.currentTimeMillis()).build());
        ImmutableMap<String, MeetingQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                MeetingQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + MONTH_INTERVAL + "???????????????");
        option.tooltip().trigger(Trigger.axis);
        option.legend("?????????");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true),
                Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.xAxis(new CategoryAxis().data(months.toArray()));
        option.yAxis(new ValueAxis());
        Bar bar = new Bar("?????????");
        bar.data(months.stream().map(
                month -> Optional.ofNullable(dateMap.get(month)).map(MeetingQueryBoResponse::getCount).orElse(0))
                .toArray());
        bar.markPoint().data(new PointData().type(MarkType.max).name("?????????"),
                new PointData().type(MarkType.min).name("?????????"));
        bar.markLine().data(new PointData().type(MarkType.average).name("?????????"));
        option.series(bar);
        return option;
    }

    /**
     * ????????????????????????????????????????????????????????????????????????4??????????????????????????????
     *
     * @return
     */
    private Option getHourlyRepair() {
        List<String> days = getDays(DAY_OF_WEEK_INTERVAL);
        Map<String, List<Integer>> hourMap = Maps.newLinkedHashMap();
        HOUR_GROUP.forEach(h -> hourMap.put(h, new ArrayList<>()));
        for (String day : days) {
            long startTimeOfDay = DateTimeKits.getStartTimeOfDay(DateTimeKits.parse(day, YYYY_MM_DD));
            long endTimeOfDay = DateTimeKits.getEndTimeOfDay(DateTimeKits.parse(day, YYYY_MM_DD));
            List<RepairQueryBoResponse> list = repairService.groupBy(RepairQueryBoRequest.builder()
                    .groupType(GroupType.HOURS).startCreateTime(startTimeOfDay).endCreateTime(endTimeOfDay).build());
            ImmutableListMultimap<String, RepairQueryBoResponse> dateMap = Multimaps.index(list,
                    o -> TIME_MAP.get(o.getDate()));
            for (String hour : HOUR_GROUP) {
                // ???4???????????????????????????????????????????????????
                ImmutableList<RepairQueryBoResponse> records = dateMap.get(hour);
                // ?????????????????????????????????
                int sum = CollectionKits.nullToEmpty(records).stream().mapToInt(RepairQueryBoResponse::getCount)
                        .sum();
                hourMap.get(hour).add(sum);
            }
        }

        List<Bar> bars = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : hourMap.entrySet()) {
            Bar bar = new Bar(entry.getKey());
            bar.stack("?????????");
            bar.itemStyle().normal().label().show(true).position("insideRight");
            bar.data(entry.getValue().toArray());
            bars.add(bar);
        }

        Option option = new Option();
        option.title().text("??????7????????????????????????????????????").left(X.center).top("7%");
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend(HOUR_GROUP.toArray());
        option.toolbox().show(true).feature(Tool.saveAsImage);
        option.calculable(true);
        option.yAxis(new CategoryAxis().data(days.toArray()));
        option.xAxis(new ValueAxis());
        option.grid().left("15%").right("1%");
        option.series(bars.toArray(new Bar[0]));
        return option;
    }

    /**
     * ??????????????????????????????
     *
     * @return
     */
    private Option getWeeklyRepair() {
        List<String> days = getDays(DAY_OF_WEEK_INTERVAL);
        List<RepairQueryBoResponse> list = repairService.groupBy(RepairQueryBoRequest.builder().groupType(GroupType.DAY)
                .startCreateTime(getTargetDayBeginTime(DAY_OF_WEEK_INTERVAL)).endCreateTime(System.currentTimeMillis())
                .build());
        ImmutableMap<String, RepairQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                RepairQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + DAY_OF_WEEK_INTERVAL + "???????????????").x(X.center);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
        option.legend().orient(Orient.vertical).left(X.left).data(days.toArray());
        Pie pie = new Pie("????????????");
        pie.radius("30%");
        pie.center("40%", "45%");
        List<Map<String, Object>> data = new ArrayList<>();
        for (String day : days) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", day);
            map.put("value", Optional.ofNullable(dateMap.get(day)).map(RepairQueryBoResponse::getCount).orElse(0));
            data.add(map);
        }
        pie.data(data.toArray());
        option.series(pie);
        return option;
    }

    /**
     * ??????30???????????????????????????
     *
     * @return
     */
    private Option getDailyRepair() {
        List<String> days = getDays(DAY_INTERVAL);
        List<RepairQueryBoResponse> list = repairService.groupBy(RepairQueryBoRequest.builder().groupType(GroupType.DAY)
                .startCreateTime(getTargetDayBeginTime(DAY_INTERVAL)).endCreateTime(System.currentTimeMillis())
                .build());
        ImmutableMap<String, RepairQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                RepairQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + DAY_INTERVAL + "?????????????????????").x(X.center);
        option.legend().x(X.left).data("?????????");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar),
                Tool.restore, Tool.saveAsImage);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}");
        option.calculable(true);
        option.yAxis(new LogAxis());
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.name("x").splitLine().show(false);
        categoryAxis.data(days.toArray());
        option.xAxis(categoryAxis);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} ??C");
        option.xAxis(valueAxis);
        Line line = new Line("?????????");
        line.data(days.stream().map(
                day -> Optional.ofNullable(dateMap.get(day)).map(RepairQueryBoResponse::getCount).orElse(0))
                .toArray());
        option.series(line);
        return option;
    }

    /**
     * ??????12??????????????????????????????
     *
     * @return
     */
    private Option getMonthlyRepair() {
        List<String> months = getMonths(12);
        List<RepairQueryBoResponse> list = repairService.groupBy(RepairQueryBoRequest.builder()
                .groupType(GroupType.MONTH).startCreateTime(getTargetMonthBeginTime(MONTH_INTERVAL))
                .endCreateTime(System.currentTimeMillis()).build());
        ImmutableMap<String, RepairQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                RepairQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + MONTH_INTERVAL + "???????????????");
        option.tooltip().trigger(Trigger.axis);
        option.legend("?????????");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true),
                Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.xAxis(new CategoryAxis().data(months.toArray()));
        option.yAxis(new ValueAxis());
        Bar bar = new Bar("?????????");
        bar.data(months.stream().map(
                month -> Optional.ofNullable(dateMap.get(month)).map(RepairQueryBoResponse::getCount).orElse(0))
                .toArray());
        bar.markPoint().data(new PointData().type(MarkType.max).name("?????????"),
                new PointData().type(MarkType.min).name("?????????"));
        bar.markLine().data(new PointData().type(MarkType.average).name("?????????"));
        option.series(bar);
        return option;
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????4??????????????????????????????
     *
     * @return
     */
    private Option getHourlyReserve() {
        List<String> days = getDays(DAY_OF_WEEK_INTERVAL);
        Map<String, List<Integer>> hourMap = Maps.newLinkedHashMap();
        HOUR_GROUP.forEach(h -> hourMap.put(h, new ArrayList<>()));
        for (String day : days) {
            long startTimeOfDay = DateTimeKits.getStartTimeOfDay(DateTimeKits.parse(day, YYYY_MM_DD));
            long endTimeOfDay = DateTimeKits.getEndTimeOfDay(DateTimeKits.parse(day, YYYY_MM_DD));
            List<ReserveQueryBoResponse> list = reserveService.groupBy(ReserveQueryBoRequest.builder()
                    .groupType(GroupType.HOURS).startCreateTime(startTimeOfDay).endCreateTime(endTimeOfDay).build());
            ImmutableListMultimap<String, ReserveQueryBoResponse> dateMap = Multimaps.index(list,
                    o -> TIME_MAP.get(o.getDate()));
            for (String hour : HOUR_GROUP) {
                // ???4???????????????????????????????????????????????????
                ImmutableList<ReserveQueryBoResponse> records = dateMap.get(hour);
                // ?????????????????????????????????
                int sum = CollectionKits.nullToEmpty(records).stream().mapToInt(ReserveQueryBoResponse::getCount)
                        .sum();
                hourMap.get(hour).add(sum);
            }
        }

        List<Bar> bars = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : hourMap.entrySet()) {
            Bar bar = new Bar(entry.getKey());
            bar.stack("???????????????");
            bar.itemStyle().normal().label().show(true).position("insideRight");
            bar.data(entry.getValue().toArray());
            bars.add(bar);
        }

        Option option = new Option();
        option.title().text("??????7??????????????????????????????????????????").left(X.center).top("7%");
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend(HOUR_GROUP.toArray());
        option.toolbox().show(true).feature(Tool.saveAsImage);
        option.calculable(true);
        option.yAxis(new CategoryAxis().data(days.toArray()));
        option.xAxis(new ValueAxis());
        option.grid().left("15%").right("1%");
        option.series(bars.toArray(new Bar[0]));
        return option;
    }

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    private Option getWeeklyReserve() {
        List<String> days = getDays(DAY_OF_WEEK_INTERVAL);
        List<ReserveQueryBoResponse> list = reserveService.groupBy(ReserveQueryBoRequest.builder()
                .groupType(GroupType.DAY).startCreateTime(getTargetDayBeginTime(DAY_OF_WEEK_INTERVAL))
                .endCreateTime(System.currentTimeMillis()).build());
        ImmutableMap<String, ReserveQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                ReserveQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + DAY_OF_WEEK_INTERVAL + "?????????????????????").x(X.center);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
        option.legend().orient(Orient.vertical).left(X.left).data(days.toArray());
        Pie pie = new Pie("??????????????????");
        pie.radius("30%");
        pie.center("40%", "45%");
        List<Map<String, Object>> data = new ArrayList<>();
        for (String day : days) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", day);
            map.put("value", Optional.ofNullable(dateMap.get(day)).map(ReserveQueryBoResponse::getCount).orElse(0));
            data.add(map);
        }
        pie.data(data.toArray());
        option.series(pie);
        return option;
    }

    /**
     * ??????30?????????????????????????????????
     *
     * @return
     */
    private Option getDailyReserve() {
        List<String> days = getDays(DAY_INTERVAL);
        List<ReserveQueryBoResponse> list = reserveService.groupBy(ReserveQueryBoRequest.builder()
                .groupType(GroupType.DAY).startCreateTime(getTargetDayBeginTime(DAY_INTERVAL))
                .endCreateTime(System.currentTimeMillis()).build());
        ImmutableMap<String, ReserveQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                ReserveQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + DAY_INTERVAL + "???????????????????????????").x(X.center);
        option.legend().x(X.left).data("???????????????");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar),
                Tool.restore, Tool.saveAsImage);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}");
        option.calculable(true);
        option.yAxis(new LogAxis());
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.name("x").splitLine().show(false);
        categoryAxis.data(days.toArray());
        option.xAxis(categoryAxis);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} ??C");
        option.xAxis(valueAxis);
        Line line = new Line("???????????????");
        line.data(days.stream().map(
                day -> Optional.ofNullable(dateMap.get(day)).map(ReserveQueryBoResponse::getCount).orElse(0))
                .toArray());
        option.series(line);
        return option;
    }

    /**
     * ??????12????????????????????????????????????
     *
     * @return
     */
    private Option getMonthlyReserve() {
        List<String> months = getMonths(12);
        List<ReserveQueryBoResponse> list = reserveService.groupBy(ReserveQueryBoRequest.builder()
                .groupType(GroupType.MONTH).startCreateTime(getTargetMonthBeginTime(MONTH_INTERVAL))
                .endCreateTime(System.currentTimeMillis()).build());
        ImmutableMap<String, ReserveQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                ReserveQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + MONTH_INTERVAL + "?????????????????????");
        option.tooltip().trigger(Trigger.axis);
        option.legend("???????????????");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true),
                Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.xAxis(new CategoryAxis().data(months.toArray()));
        option.yAxis(new ValueAxis());
        Bar bar = new Bar("???????????????");
        bar.data(months.stream().map(
                month -> Optional.ofNullable(dateMap.get(month)).map(ReserveQueryBoResponse::getCount).orElse(0))
                .toArray());
        bar.markPoint().data(new PointData().type(MarkType.max).name("?????????"),
                new PointData().type(MarkType.min).name("?????????"));
        bar.markLine().data(new PointData().type(MarkType.average).name("?????????"));
        option.series(bar);
        return option;
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????4??????????????????????????????
     *
     * @return
     */
    private Option getHourlyRoom() {
        List<String> days = getDays(DAY_OF_WEEK_INTERVAL);
        Map<String, List<Integer>> hourMap = Maps.newLinkedHashMap();
        HOUR_GROUP.forEach(h -> hourMap.put(h, new ArrayList<>()));
        for (String day : days) {
            long startTimeOfDay = DateTimeKits.getStartTimeOfDay(DateTimeKits.parse(day, YYYY_MM_DD));
            long endTimeOfDay = DateTimeKits.getEndTimeOfDay(DateTimeKits.parse(day, YYYY_MM_DD));
            List<RoomQueryBoResponse> list = roomService.groupBy(RoomQueryBoRequest.builder().groupType(GroupType.HOURS)
                    .startCreateTime(startTimeOfDay).endCreateTime(endTimeOfDay).build());
            ImmutableListMultimap<String, RoomQueryBoResponse> dateMap = Multimaps.index(list,
                    o -> TIME_MAP.get(o.getDate()));
            for (String hour : HOUR_GROUP) {
                // ???4???????????????????????????????????????????????????
                ImmutableList<RoomQueryBoResponse> records = dateMap.get(hour);
                // ?????????????????????????????????
                int sum = CollectionKits.nullToEmpty(records).stream().mapToInt(RoomQueryBoResponse::getCount)
                        .sum();
                hourMap.get(hour).add(sum);
            }
        }

        List<Bar> bars = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : hourMap.entrySet()) {
            Bar bar = new Bar(entry.getKey());
            bar.stack("????????????");
            bar.itemStyle().normal().label().show(true).position("insideRight");
            bar.data(entry.getValue().toArray());
            bars.add(bar);
        }

        Option option = new Option();
        option.title().text("??????7???????????????????????????????????????").left(X.center).top("7%");
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend(HOUR_GROUP.toArray());
        option.toolbox().show(true).feature(Tool.saveAsImage);
        option.calculable(true);
        option.yAxis(new CategoryAxis().data(days.toArray()));
        option.xAxis(new ValueAxis());
        option.grid().left("15%").right("1%");
        option.series(bars.toArray(new Bar[0]));
        return option;
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    private Option getWeeklyRoom() {
        List<String> days = getDays(DAY_OF_WEEK_INTERVAL);
        List<RoomQueryBoResponse> list = roomService.groupBy(RoomQueryBoRequest.builder().groupType(GroupType.DAY)
                .startCreateTime(getTargetDayBeginTime(DAY_OF_WEEK_INTERVAL)).endCreateTime(System.currentTimeMillis())
                .build());
        ImmutableMap<String, RoomQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                RoomQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + DAY_OF_WEEK_INTERVAL + "??????????????????").x(X.center);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
        option.legend().orient(Orient.vertical).left(X.left).data(days.toArray());
        Pie pie = new Pie("???????????????");
        pie.radius("30%");
        pie.center("40%", "45%");
        List<Map<String, Object>> data = new ArrayList<>();
        for (String day : days) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", day);
            map.put("value", Optional.ofNullable(dateMap.get(day)).map(RoomQueryBoResponse::getCount).orElse(0));
            data.add(map);
        }
        pie.data(data.toArray());
        option.series(pie);
        return option;
    }

    /**
     * ??????30??????????????????????????????
     *
     * @return
     */
    private Option getDailyRoom() {
        List<String> days = getDays(DAY_INTERVAL);
        List<RoomQueryBoResponse> list = roomService.groupBy(RoomQueryBoRequest.builder().groupType(GroupType.DAY)
                .startCreateTime(getTargetDayBeginTime(DAY_INTERVAL)).endCreateTime(System.currentTimeMillis())
                .build());
        ImmutableMap<String, RoomQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                RoomQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + DAY_INTERVAL + "????????????????????????").x(X.center);
        option.legend().x(X.left).data("????????????");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar),
                Tool.restore, Tool.saveAsImage);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}");
        option.calculable(true);
        option.yAxis(new LogAxis());
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.name("x").splitLine().show(false);
        categoryAxis.data(days.toArray());
        option.xAxis(categoryAxis);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} ??C");
        option.xAxis(valueAxis);
        Line line = new Line("????????????");
        line.data(days.stream().map(
                day -> Optional.ofNullable(dateMap.get(day)).map(RoomQueryBoResponse::getCount).orElse(0))
                .toArray());
        option.series(line);
        return option;
    }

    /**
     * ??????12?????????????????????????????????
     *
     * @return
     */
    private Option getMonthlyRoom() {
        List<String> months = getMonths(12);
        List<RoomQueryBoResponse> list = roomService.groupBy(RoomQueryBoRequest.builder().groupType(GroupType.MONTH)
                .startCreateTime(getTargetMonthBeginTime(MONTH_INTERVAL)).endCreateTime(System.currentTimeMillis())
                .build());
        ImmutableMap<String, RoomQueryBoResponse> dateMap = Maps.uniqueIndex(list,
                RoomQueryBoResponse::getDate);
        Option option = new Option();
        option.title().text("??????" + MONTH_INTERVAL + "??????????????????");
        option.tooltip().trigger(Trigger.axis);
        option.legend("????????????");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true),
                Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.xAxis(new CategoryAxis().data(months.toArray()));
        option.yAxis(new ValueAxis());
        Bar bar = new Bar("????????????");
        bar.data(months.stream().map(
                month -> Optional.ofNullable(dateMap.get(month)).map(RoomQueryBoResponse::getCount).orElse(0))
                .toArray());
        bar.markPoint().data(new PointData().type(MarkType.max).name("?????????"),
                new PointData().type(MarkType.min).name("?????????"));
        bar.markLine().data(new PointData().type(MarkType.average).name("?????????"));
        option.series(bar);
        return option;
    }

}
