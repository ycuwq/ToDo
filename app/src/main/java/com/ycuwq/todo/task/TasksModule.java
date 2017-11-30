package com.ycuwq.todo.task;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 提供Tasks中的Fragment
 * Created by 杨晨 on 2017/11/29.
 */
@Module
public abstract class TasksModule {
	@ContributesAndroidInjector
	abstract TasksFragment tasksFragment();

}
