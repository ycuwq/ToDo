package com.ycuwq.todo.di;

import com.ycuwq.todo.task.TaskActivity;

import dagger.Component;

/**
 * 为Activity提供注入依赖
 * Created by 杨晨 on 2017/5/12.
 */
@FragmentScoped
@Component(dependencies = TaskRepositoryComponent.class, modules = TaskViewModelModule.class)
public interface TaskViewModelComponent {
	void inject(TaskActivity activity);
}
