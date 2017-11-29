package com.ycuwq.todo.di;

import android.app.Application;

import com.ycuwq.todo.App;
import com.ycuwq.todo.data.source.TasksRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by 杨晨 on 2017/11/29.
 */
@Singleton
@Component(modules = {AppModule.class,
		TasksRepositoryModule.class,
		ActivityBindingModule.class,
		AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {

	TasksRepository getTasksRepository();

	@Component.Builder
	interface Builder {
		@BindsInstance
		Builder application(Application application);
		AppComponent build();
	}
	void inject(App app);
}
