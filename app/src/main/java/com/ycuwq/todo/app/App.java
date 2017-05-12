package com.ycuwq.todo.app;

import android.app.Application;

import com.ycuwq.todo.data.source.DaggerTaskRepositoryComponent;
import com.ycuwq.todo.data.source.TaskRepositoryComponent;

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
