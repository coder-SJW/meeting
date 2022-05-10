package com.senior.domain.bo.request;

import java.util.List;

import com.senior.common.base.PageQuery;
import com.senior.common.enums.GroupType;
import com.senior.domain.model.MeetingDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 会议室(Meeting) QueryBoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class MeetingQueryBoRequest extends MeetingDo implements PageQuery {

    /**
     * id列表
     */
    private List<Long> ids;

    /**
     * 会议名称
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    private Long startCreateTime;
    private Long endCreateTime;

    private Long startUpdateTime;
    private Long endUpdateTime;

    /**
     * 会议室
     */
    private String roomName;

    private Long roomId;
    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 聚合维度
     */
    private GroupType groupType;
    /**
     * 聚合字段
     */
    private List<String> groupFields;

}
