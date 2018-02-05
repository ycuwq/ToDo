package com.ycuwq.todo.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.ycuwq.todo.data.source.local.AppDb;
import com.ycuwq.todo.data.source.local.dao.TaskDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 为App提供数据库的相关类
 * Created by 杨晨 on 2017/11/29.
 */
@Module
public class RepositoryModule {

	static final String DATABASE_NAME = "todo-db";

	@Singleton
	@Provides
	AppDb provideAppDb(Context context) {
		return Room.databaseBuilder(context.getApplicationContext(), AppDb.class, DATABASE_NAME).build();
	}

    @Singleton
    @Provides
	TaskDao provideTaskDao(AppDb appDb) {
	    return appDb.taskDao();
    }

}
