package com.ycuwq.todo.di;

import dagger.Binds;
import dagger.Module;

/**
 * Created by 杨晨 on 2017/5/12.
 */
@Module
abstract class TaskViewModelModule {
	@Binds
	abstract TaskViewModelModule provideTaskViewModelModule(TaskViewModelModule taskViewModelModule);
}
