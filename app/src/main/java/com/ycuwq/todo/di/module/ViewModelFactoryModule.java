package com.ycuwq.todo.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ycuwq.todo.AppViewModelFactory;
import com.ycuwq.todo.view.edittask.EditTaskViewModel;
import com.ycuwq.todo.view.tasks.TasksViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * ViewModel提供者
 * Created by 杨晨 on 2017/5/12.
 */
@Module
public abstract class ViewModelFactoryModule {
	@Binds
	@IntoMap
	@ViewModelKey(TasksViewModel.class)
	abstract ViewModel bindTasksViewModel(TasksViewModel tasksViewModel);

	@Binds
	@IntoMap
	@ViewModelKey(EditTaskViewModel.class)
	abstract ViewModel bindEditTaskViewModel(EditTaskViewModel viewModel);

	@Binds
	abstract ViewModelProvider.Factory provideTaskViewModelModule(AppViewModelFactory appViewModelFactory);
}
