package com.ycuwq.todo.data.source;

import com.ycuwq.todo.app.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 注入TaskRepository的接口
 * Created by 杨晨 on 2017/5/12.
 */
@Singleton
@Component(modules = {ApplicationModule.class, DataSourceModule.class})
public interface TaskRepositoryComponent {
	TaskRepository getTaskRepository();
}
