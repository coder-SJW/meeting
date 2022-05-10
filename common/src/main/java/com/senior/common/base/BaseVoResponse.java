/*
 * @(#) BaseVoResponse.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.base;

/**
 * 输出VO （Response View Object）： <br/>
 * <p>
 * 定义：显示层输出对象，通常是controller向web层输出的渲染对象或者api接口输出的对象。 <br/>
 * <p>
 * 命名规范：按照动名词语法进行命名，UpdateXxxVoResponse 、QueryXxxVoResponse<br/>
 *
 * @author senior
 */
public interface BaseVoResponse extends BaseVo {
}
