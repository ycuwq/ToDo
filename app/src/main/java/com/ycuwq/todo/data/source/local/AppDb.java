package com.ycuwq.todo.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.dao.TaskDao;

/**
 * Task的仓库，
 * Created by 杨晨 on 2017/5/12.
 */
@Database(entities = {Task.class}, version = 1)
public abstract class AppDb extends RoomDatabase{
	public abstract TaskDao taskDao();
}
