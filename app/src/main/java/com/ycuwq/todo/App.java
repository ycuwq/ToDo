package com.ycuwq.todo;

import android.app.Application;

import com.ycuwq.todo.di.ApplicationModule;
import com.ycuwq.todo.di.DaggerTaskRepositoryComponent;
import com.ycuwq.todo.di.TaskRepositoryComponent;

/**
 * 自定义Application
 * Created by 杨晨 on 2017/5/7.
 */

public class App extends Application{

	private TaskRepositoryComponent mRepositoryComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		mRepositoryComponent = DaggerTaskRepositoryComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.build();
	}

	public TaskRepositoryComponent getRepositoryComponent() {
		return mRepositoryComponent;
	}
}
