package com.ycuwq.todo.di.module;

import com.ycuwq.todo.view.edittask.EditTaskActivity;
import com.ycuwq.todo.view.edittask.EditTaskModule;
import com.ycuwq.todo.view.tasks.TasksActivity;
import com.ycuwq.todo.view.tasks.TasksModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 在 AppComponent 中使用，为了方便的对Activity进行依赖注入。
 * Created by 杨晨 on 2017/11/29.
 */
@Module
public abstract class ActivityBindingModule {
	@ContributesAndroidInjector(modules = {EditTaskModule.class})
	abstract EditTaskActivity addEditTaskActivityInjector();

	@ContributesAndroidInjector(modules = {TasksModule.class})
	abstract TasksActivity taskActivityInjector();
}
