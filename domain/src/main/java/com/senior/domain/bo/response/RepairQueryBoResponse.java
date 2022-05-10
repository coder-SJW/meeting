package com.senior.domain.bo.response;

import com.senior.domain.model.RepairDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 报修(Repair) QueryBoResponse
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class RepairQueryBoResponse extends RepairDo {
    private Integer count;
    private String date;
}
