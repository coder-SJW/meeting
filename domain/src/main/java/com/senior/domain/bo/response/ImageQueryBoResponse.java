package com.senior.domain.bo.response;

import com.senior.domain.model.ImageDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 图片(Image) QueryBoResponse
 *
 * @author senior
 * @since 2021-08-16 22:13:18
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ImageQueryBoResponse extends ImageDo {
    private Integer count;
    private String date;
}
