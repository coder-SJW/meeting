package com.senior.domain.bo.request;

import com.senior.domain.model.DepartmentDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 部门(Department) UpdateBoRequest
 *
 * @author senior
 * @since 2021-08-17 11:21:22
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class DepartmentUpdateBoRequest extends DepartmentDo {
}
