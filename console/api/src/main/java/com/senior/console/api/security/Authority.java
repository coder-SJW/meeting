package com.senior.console.api.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限枚举
 */
@AllArgsConstructor
public enum Authority {
    // 用户账号
    ACCOUNT(AuthorityType.MENU, null, "用户账号菜单"),
    ACCOUNT_CREATE(AuthorityType.BUTTON, ACCOUNT, "新增用户"),
    ACCOUNT_DELETE(AuthorityType.BUTTON, ACCOUNT, "删除用户"),
    ACCOUNT_UPDATE(AuthorityType.BUTTON, ACCOUNT, "修改用户"),
    ACCOUNT_QUERY(AuthorityType.BUTTON, ACCOUNT, "查询用户"),
    ACCOUNT_PERSONAL(AuthorityType.BUTTON, ACCOUNT, "个人信息修改"),

    // 角色
    ROLE(AuthorityType.MENU, null, "角色菜单"),
    ROLE_CREATE(AuthorityType.BUTTON, ROLE, "新增角色"),
    ROLE_DELETE(AuthorityType.BUTTON, ROLE, "删除角色"),
    ROLE_UPDATE(AuthorityType.BUTTON, ROLE, "修改角色"),
    ROLE_QUERY(AuthorityType.BUTTON, ROLE, "查询用户"),
    // 日志
    SYS_LOG(AuthorityType.MENU, null, "日志菜单"),
    SYS_LOG_QUERY(AuthorityType.BUTTON, SYS_LOG, "查询日志"),

    // 部门
    DEPARTMENT(AuthorityType.MENU, null, "部门菜单"),
    DEPARTMENT_CREATE(AuthorityType.BUTTON, DEPARTMENT, "新增部门"),
    DEPARTMENT_DELETE(AuthorityType.BUTTON, DEPARTMENT, "删除部门"),
    DEPARTMENT_UPDATE(AuthorityType.BUTTON, DEPARTMENT, "修改部门"),
    DEPARTMENT_QUERY(AuthorityType.BUTTON, DEPARTMENT, "查询部门"),
    DEPARTMENT_EXPORT(AuthorityType.BUTTON, DEPARTMENT, "导出部门"),

    // 会议室
    MEETING(AuthorityType.MENU, null, "会议室菜单"),
    MEETING_CREATE(AuthorityType.BUTTON, MEETING, "新增会议室"),
    MEETING_DELETE(AuthorityType.BUTTON, MEETING, "删除会议室"),
    MEETING_UPDATE(AuthorityType.BUTTON, MEETING, "修改会议室"),
    MEETING_QUERY(AuthorityType.BUTTON, MEETING, "查询会议室"),
    MEETING_EXPORT(AuthorityType.BUTTON, MEETING, "导出会议室"),
    MY_MEETING(AuthorityType.BUTTON, MEETING, "我的会议"),

    // 报修
    REPAIR(AuthorityType.MENU, null, "报修菜单"),
    REPAIR_CREATE(AuthorityType.BUTTON, REPAIR, "新增报修"),
    REPAIR_DELETE(AuthorityType.BUTTON, REPAIR, "删除报修"),
    REPAIR_UPDATE(AuthorityType.BUTTON, REPAIR, "修改报修"),
    REPAIR_QUERY(AuthorityType.BUTTON, REPAIR, "查询报修"),
    REPAIR_EXPORT(AuthorityType.BUTTON, REPAIR, "导出报修"),

    // 预约记录
    RESERVE(AuthorityType.MENU, null, "预约记录菜单"),
    RESERVE_CREATE(AuthorityType.BUTTON, RESERVE, "新增预约记录"),
    RESERVE_DELETE(AuthorityType.BUTTON, RESERVE, "删除预约记录"),
    RESERVE_UPDATE(AuthorityType.BUTTON, RESERVE, "修改预约记录"),
    RESERVE_QUERY(AuthorityType.BUTTON, RESERVE, "查询预约记录"),
    RESERVE_EXPORT(AuthorityType.BUTTON, RESERVE, "导出预约记录"),

    // 会议室
    ROOM(AuthorityType.MENU, null, "会议室菜单"),
    ROOM_CREATE(AuthorityType.BUTTON, ROOM, "新增会议室"),
    ROOM_DELETE(AuthorityType.BUTTON, ROOM, "删除会议室"),
    ROOM_UPDATE(AuthorityType.BUTTON, ROOM, "修改会议室"),
    ROOM_QUERY(AuthorityType.BUTTON, ROOM, "查询会议室"),
    ROOM_EXPORT(AuthorityType.BUTTON, ROOM, "导出会议室"),

    ;

    public static final List<String> ALL_AUTH;
    public static final List<Authority> MENU_AUTH;
    public static final List<Authority> BUTTON_AUTH;

    static {
        ImmutableList.Builder<String> builder = ImmutableList.<String>builder();
        for (Authority value : Authority.values()) {
            builder.add(value.name());
        }
        ALL_AUTH = builder.build();
        MENU_AUTH = ImmutableList.copyOf(Arrays.stream(Authority.values())
                .filter(x -> x.type == AuthorityType.MENU).collect(Collectors.toList()));
        BUTTON_AUTH = ImmutableList.copyOf(Arrays.stream(Authority.values())
                .filter(x -> x.type == AuthorityType.BUTTON).collect(Collectors.toList()));
    }

    @Getter
    private final AuthorityType type;
    /** 按钮权限时，表示所在的菜单 */
    @Getter
    private final Authority parent;
    @Getter
    private final String description;

    public static Authority valueOfOrNull(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
