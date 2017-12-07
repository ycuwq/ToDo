package com.ycuwq.todo.edittask;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.ycuwq.todo.di.module.ViewModelFactoryModule;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * 为{@link EditTaskActivity}提供依赖注入。
 * Created by yangchen on 2017/12/1.
 */
@Module(includes = ViewModelFactoryModule.class)
public abstract class EditTaskModule {


	@ContributesAndroidInjector
	abstract EditTaskFragment editTaskFragment();

	@ContributesAndroidInjector
	abstract EditScheduleFragment editScheduleFragment();

	@ContributesAndroidInjector
	abstract EditBirthdayFragment editBirthdayFragment();

	@ContributesAndroidInjector
	abstract EditAnniversaryFragment editAnniversaryFragment();

	@Provides
	static EditTaskViewModel provideEditTaskViewModel(EditTaskFragment fragment, ViewModelProvider.Factory mViewModelFactory) {
		return ViewModelProviders.of(fragment, mViewModelFactory).get(EditTaskViewModel.class);
	}

}
