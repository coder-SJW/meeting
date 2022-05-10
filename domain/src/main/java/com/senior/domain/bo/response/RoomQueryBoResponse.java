package com.senior.domain.bo.response;

import com.senior.domain.model.RoomDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 会议室(Room) QueryBoResponse
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class RoomQueryBoResponse extends RoomDo {
    private Integer count;
    private String date;
}
