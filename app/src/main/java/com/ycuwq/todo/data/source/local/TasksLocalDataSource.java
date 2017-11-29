package com.ycuwq.todo.data.source.local;

import com.ycuwq.todo.data.bean.Task;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 本地数据库
 * 利用Dagger2 实现单例模式
 * Created by 杨晨 on 2017/5/9.
 */
@Singleton
public class TasksLocalDataSource implements TasksDataSource {


	@Inject
	public TasksLocalDataSource() {
		// TODO: 2017/11/29 是否需要使用Context
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
