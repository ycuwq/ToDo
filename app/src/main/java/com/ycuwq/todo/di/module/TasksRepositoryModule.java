package com.ycuwq.todo.di.module;

import com.ycuwq.todo.data.source.TasksRepository;
import com.ycuwq.todo.data.source.local.TasksLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 为App提供Repository等相关类
 * Created by 杨晨 on 2017/11/29.
 */
@Module
public class TasksRepositoryModule {

	@Singleton
	@Provides
	TasksLocalDataSource provideTasksLocalDataSource() {
		return new TasksLocalDataSource();
	}

	@Singleton
	@Provides
	TasksRepository provideTasksRepository(TasksLocalDataSource tasksLocalDataSource) {
		return new TasksRepository(tasksLocalDataSource);
	}

}
