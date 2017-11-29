package com.ycuwq.todo;

import com.ycuwq.todo.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


/**
 * 自定义Application
 * Created by 杨晨 on 2017/5/7.
 */

public class App extends DaggerApplication {



	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		return DaggerAppComponent.builder().application(this).build();
	}


}
