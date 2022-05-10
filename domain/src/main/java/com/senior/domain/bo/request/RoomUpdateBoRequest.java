package com.senior.domain.bo.request;

import com.senior.domain.model.RoomDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 会议室(Room) UpdateBoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class RoomUpdateBoRequest extends RoomDo {
}
