package com.ycuwq.todo;


import com.ycuwq.todo.di.AppInjector;
import com.ycuwq.todo.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;


/**
 * 自定义Application
 * Created by 杨晨 on 2017/5/7.
 */

public class App extends DaggerApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		} else {
			// TODO: 2017/12/11 崩溃保存
			Timber.plant(new Timber.DebugTree());
		}

		AppInjector.init(this);
	}

	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		return DaggerAppComponent.builder().application(this).build();
	}


}
