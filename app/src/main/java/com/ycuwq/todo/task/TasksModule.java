package com.ycuwq.todo.task;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by 杨晨 on 2017/11/29.
 */
@Module
public abstract class TasksModule {
	// TODO: 2017/11/29 Fragment的作用域
	@ContributesAndroidInjector
	abstract TasksFragment tasksFragment();

}
