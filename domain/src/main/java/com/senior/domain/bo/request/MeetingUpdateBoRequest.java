package com.senior.domain.bo.request;

import com.senior.domain.model.MeetingDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 会议室(Meeting) UpdateBoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class MeetingUpdateBoRequest extends MeetingDo {
}
