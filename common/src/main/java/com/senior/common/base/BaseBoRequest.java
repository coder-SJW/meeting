/*
 * @(#) BaseBoRequest.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.base;

/**
 * 输入BO（Request Business Object）：<br/>
 * <p>
 * 定义：输入业务对象。由 business 层输入的封装业务逻辑的对象，<br/>
 * 当业务对象非常简单，BO=DO时，直接新建BO继承DO， <br/>
 * 当BO出现不等于DO时，和DO没太大关系，必须强制增加BO，不允许DO增加字段当BO使用了。 <br/>
 * <p>
 * 命名规范：按照动名词语法进行命名，UpdateXxxBoRequest、QueryXxxBoRequest <br/>
 *
 * @author senior
 */
public interface BaseBoRequest extends BaseBo {
}
