package com.senior.console.api.controller.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.senior.common.base.BaseVoRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门(Department) CreateVoRequest
 *
 * @author senior
 * @since 2021-08-17 11:43:36
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCreateVoRequest implements BaseVoRequest {

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    @Length(max = 255, message = "name长度不能超过255")
    @NotNull
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String description;

}
