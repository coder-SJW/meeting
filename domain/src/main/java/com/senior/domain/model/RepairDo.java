package com.senior.domain.model;

import com.senior.common.base.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 报修(Repair) Do
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RepairDo implements BaseDo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 状态;1:未处理 2:处理中 3: 已完成
     */
    private Integer status;

    /**
     * 内容
     */
    private String content;

    /**
     * 会议室
     */
    private Long roomId;

    /**
     * 报修人
     */
    private Long accountId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

}
