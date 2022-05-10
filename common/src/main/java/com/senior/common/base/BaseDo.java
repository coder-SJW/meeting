/*
 * @(#) BaseBo.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.base;

import java.io.Serializable;

/**
 * DO（Data Object） <br/>
 * <p>
 * 定义：与数据库表结构一一对应，通过 DAO 层向上输入和输出数据源对象。 <br/>
 * 命名规范：与数据库表对应 如表为product,该DO命名为 ProductDo <br/>
 *
 * @author senior
 */
public interface BaseDo extends Serializable {
}
