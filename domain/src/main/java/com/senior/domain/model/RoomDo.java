package com.senior.domain.model;

import java.util.List;

import com.senior.common.base.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 会议室(Room) Do
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDo implements BaseDo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 会议室
     */
    private String name;

    /**
     * 实景照片
     */
    private List<Long> roomImageIds;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    private String type;

}
