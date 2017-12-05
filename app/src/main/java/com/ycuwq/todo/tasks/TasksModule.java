package com.ycuwq.todo.tasks;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 提供Tasks中所需要的依赖注入的内容
 * Created by 杨晨 on 2017/11/29.
 */
@Module
public abstract class TasksModule {
	@ContributesAndroidInjector
	abstract TasksFragment tasksFragment();

}
