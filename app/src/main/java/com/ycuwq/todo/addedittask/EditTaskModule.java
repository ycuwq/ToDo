package com.ycuwq.todo.addedittask;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 为{@link EditTaskActivity}提供依赖注入。
 * Created by yangchen on 2017/12/1.
 */
@Module
public abstract class EditTaskModule {
	@ContributesAndroidInjector
	abstract EditTaskFragment editTaskFragment();

	@ContributesAndroidInjector
	abstract EditScheduleFragment editScheduleFragment();

	@ContributesAndroidInjector
	abstract EditBirthdayFragment editBirthdayFragment();

	@ContributesAndroidInjector
	abstract EditAnniversaryFragment editAnniversaryFragment();
}
