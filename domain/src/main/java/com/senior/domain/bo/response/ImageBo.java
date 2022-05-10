
package com.senior.domain.bo.response;

import com.senior.common.base.BaseBo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author senior
 * @version 2021-08-18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageBo implements BaseBo {
    private String name;
    private String url;
    private Long imageId;
}
