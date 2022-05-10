package com.senior.domain.model;

import com.senior.common.base.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 图片(Image) Do
 *
 * @author senior
 * @since 2021-08-16 22:13:18
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDo implements BaseDo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 图片base64
     */
    private String data;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

}
