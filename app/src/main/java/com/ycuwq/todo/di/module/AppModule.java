package com.ycuwq.todo.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * 提供Context
 * Created by 杨晨 on 2017/5/12.
 */
@Module
public abstract class AppModule {
	@Binds
	abstract Context bindContext(Application application);
}
