package com.ycuwq.todo.di.component;

import android.app.Application;

import com.ycuwq.todo.App;
import com.ycuwq.todo.di.module.ActivityBindingModule;
import com.ycuwq.todo.di.module.AppModule;
import com.ycuwq.todo.di.module.RepositoryModule;
import com.ycuwq.todo.di.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * App注入器
 * Created by 杨晨 on 2017/11/29.
 */
@Singleton
@Component(modules = {AppModule.class,
		RepositoryModule.class,
		ActivityBindingModule.class,
		ViewModelFactoryModule.class,
		AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {

	@Component.Builder
	interface Builder {
		@BindsInstance
		Builder application(Application application);
		AppComponent build();
	}
}
