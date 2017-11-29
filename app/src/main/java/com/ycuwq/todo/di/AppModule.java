package com.ycuwq.todo.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by 杨晨 on 2017/5/12.
 */
@Module
public abstract class AppModule {

	@Binds
	abstract Context bindContext(Application application);
}
