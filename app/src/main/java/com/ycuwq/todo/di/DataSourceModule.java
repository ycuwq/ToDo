package com.ycuwq.todo.di;

import com.ycuwq.todo.data.source.local.TasksDataSource;
import com.ycuwq.todo.data.source.local.TasksLocalDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Dagger2 为DataSource提供依赖注入的Module
 * Created by 杨晨 on 2017/5/12.
 */
@Module
public abstract class DataSourceModule {

	@Binds
	@Singleton
	@Local
	public abstract TasksDataSource provideLocalDataSource(TasksLocalDataSource localDataSource);
}
