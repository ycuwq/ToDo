package com.ycuwq.todo.app;

import android.app.Application;
import android.support.annotation.NonNull;

import com.ycuwq.todo.data.bean.DaoMaster;
import com.ycuwq.todo.data.bean.DaoSession;

/**
 * Created by 杨晨 on 2017/5/7.
 */

public class App extends Application{

	private static DaoSession mDaoSession;

	@Override
	public void onCreate() {
		super.onCreate();
		DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "tasks-db");
		DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
		mDaoSession = daoMaster.newSession();
	}

	@NonNull
	public static DaoSession getDaoSession() {
		return mDaoSession;
	}
}
