package com.senior.domain.bo.request;

import java.util.List;

import com.senior.common.base.PageQuery;
import com.senior.common.enums.GroupType;
import com.senior.domain.model.ReserveDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 预约记录(Reserve) QueryBoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ReserveQueryBoRequest extends ReserveDo implements PageQuery {

    /**
     * id列表
     */
    private List<Long> ids;

    /**
     * 内容
     */
    private String content;

    private Long startReserveStartTime;
    private Long endReserveStartTime;

    private Long startReserveEndTime;
    private Long endReserveEndTime;

    private Long startCreateTime;
    private Long endCreateTime;

    private Long startUpdateTime;
    private Long endUpdateTime;

    /**
     * 会议室
     */
    private String roomName;

    /**
     * 预约人
     */
    private String accountPassport;

    private Long accountId;
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
