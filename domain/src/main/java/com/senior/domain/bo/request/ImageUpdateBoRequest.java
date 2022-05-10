package com.senior.domain.bo.request;

import com.senior.domain.model.ImageDo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 图片(Image) UpdateBoRequest
 *
 * @author senior
 * @since 2021-08-16 22:13:18
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ImageUpdateBoRequest extends ImageDo {
}
