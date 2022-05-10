/*
 * @(#) BaseVoRequest.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.base;

/**
 * 输入VO（Request View Object）： <br/>
 * <p>
 * 定义：显示层输入对象，通常是 web 向controller输入的查询或者更新对象，或者api层的查询或者更新对象。 <br/>
 * <p>
 * 命名规范：按照动名词语法进行命名，UpdateXxxVoRequest、QueryXxxVoRequest <br/>
 *
 * @author senior
 */
public interface BaseVoRequest extends BaseVo {
}
