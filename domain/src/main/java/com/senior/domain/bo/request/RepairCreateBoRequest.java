package com.senior.domain.bo.request;

import com.senior.domain.model.RepairDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 报修(Repair) CreateBoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class RepairCreateBoRequest extends RepairDo {

}
