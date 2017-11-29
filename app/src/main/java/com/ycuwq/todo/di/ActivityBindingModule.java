package com.ycuwq.todo.di;

import com.ycuwq.todo.task.TasksActivity;
import com.ycuwq.todo.task.TasksModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by 杨晨 on 2017/11/29.
 */
@Module
public abstract class ActivityBindingModule {
	@ContributesAndroidInjector(modules = {TasksModule.class})
	abstract TasksActivity taskActivityInjector();
}
