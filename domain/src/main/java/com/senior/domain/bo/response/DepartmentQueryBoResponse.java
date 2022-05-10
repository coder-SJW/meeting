package com.senior.domain.bo.response;

import com.senior.domain.model.DepartmentDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 部门(Department) QueryBoResponse
 *
 * @author senior
 * @since 2021-08-17 11:21:22
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class DepartmentQueryBoResponse extends DepartmentDo {
    private Integer count;
    private String date;
}
