package com.ycuwq.todo.data.source.local;

import android.content.Context;

import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.di.TaskDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 本地数据库
 * 利用Dagger2 实现单例模式
 * Created by 杨晨 on 2017/5/9.
 */
@Singleton
public class TaskLocalDataSource implements TaskDataSource{


	@Inject
	public TaskLocalDataSource(Context context) {

	}

	@Override
	public void saveTask(Task task) {
	}

	@Override
	public void saveTasks(List<Task> tasks) {
	}

	@Override
	public void getTasks(GetTasksCallback callback) {

	}

	@Override
	public void updateTask(Task task) {
	}

	@Override
	public void deleteTask(long id) {
	}

	@Override
	public void deleteTasks(List<Task> list) {
	}

	@Override
	public void deleteAll() {
	}
}
