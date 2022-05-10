package com.senior.console.api.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author sjw
 * @Description
 * @Date 18:28 2022/4/29
 **/
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetStatusDTO {
    private Long id;
    private String status;
}
