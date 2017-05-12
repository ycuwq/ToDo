package com.ycuwq.todo.common.util;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 在Dagger2中一个无作用域的组件不能被局部变量所依赖。也就是说TaskRepository实现了单例模式，所有
 * 依赖于TAskRepository的类也必须实现全局单例{@code @Singleton}或局部单例{@code @Scope}
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScoped {
}
