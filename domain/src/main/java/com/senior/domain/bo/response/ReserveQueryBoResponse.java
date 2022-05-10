package com.senior.domain.bo.response;

import com.senior.domain.model.ReserveDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 预约记录(Reserve) QueryBoResponse
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ReserveQueryBoResponse extends ReserveDo {
    private Integer count;
    private String date;
}
