package com.ycuwq.todo.data.source;

import com.ycuwq.todo.data.source.local.TaskLocalDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Dagger2 为DataSource提供依赖注入的Module
 * Created by 杨晨 on 2017/5/12.
 */
@Module
abstract class DataSourceModule {

	@Binds
	@Singleton
	@Local
	abstract TaskDataSource provideLocalDataSource(TaskLocalDataSource localDataSource);
}
