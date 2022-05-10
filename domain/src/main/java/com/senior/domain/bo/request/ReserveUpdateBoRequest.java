package com.senior.domain.bo.request;

import com.senior.domain.model.ReserveDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 预约记录(Reserve) UpdateBoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ReserveUpdateBoRequest extends ReserveDo {
}
