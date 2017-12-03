package com.ycuwq.todo.addedittask;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 为{@link AddEditTaskActivity}提供依赖注入。
 * Created by yangchen on 2017/12/1.
 */
@Module
public abstract class AddEditTaskModule {
	@ContributesAndroidInjector
	abstract AddEditTaskFragment addEditTaskFragment();
}
