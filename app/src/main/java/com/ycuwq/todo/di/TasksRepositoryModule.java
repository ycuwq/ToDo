package com.ycuwq.todo.di;

import com.ycuwq.todo.data.source.local.TasksDataSource;
import com.ycuwq.todo.data.source.local.TasksLocalDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by 杨晨 on 2017/11/29.
 */
@Module
public abstract class TasksRepositoryModule {

	@Singleton
	@Binds
	@Local
	abstract TasksDataSource provideTasksLocalDataSource(TasksLocalDataSource dataSource);
}
