package com.ycuwq.todo.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 数据
 * Created by 杨晨 on 2017/5/12.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Local {
}
