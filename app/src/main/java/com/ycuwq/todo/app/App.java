package com.ycuwq.todo.app;

import android.app.Application;

import com.ycuwq.todo.data.bean.DaoMaster;
import com.ycuwq.todo.data.bean.DaoSession;
import com.ycuwq.todo.data.source.DaggerTaskRepositoryComponent;
import com.ycuwq.todo.data.source.TaskRepositoryComponent;

import org.greenrobot.greendao.database.Database;

/**
 * 自定义Application
 * Created by 杨晨 on 2017/5/7.
 */

public class App extends Application{

	private TaskRepositoryComponent mRepositoryComponent;
	private DaoSession daoSession;

	@Override
	public void onCreate() {
		super.onCreate();
		mRepositoryComponent = DaggerTaskRepositoryComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.build();

		DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
		Database db = helper.getWritableDb();
		daoSession = new DaoMaster(db).newSession();
	}

	public DaoSession getDaoSession() {
		return daoSession;
	}

	public TaskRepositoryComponent getRepositoryComponent() {
		return mRepositoryComponent;
	}
}
