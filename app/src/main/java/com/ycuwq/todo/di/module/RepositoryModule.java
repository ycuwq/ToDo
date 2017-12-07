package com.ycuwq.todo.di.module;

import com.ycuwq.todo.data.source.local.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 为App提供数据库的相关类
 * Created by 杨晨 on 2017/11/29.
 */
@Module
public class RepositoryModule {

	@Singleton
	@Provides
	AppDatabase provideTasksRepository() {
		return new AppDatabase();
	}

}
