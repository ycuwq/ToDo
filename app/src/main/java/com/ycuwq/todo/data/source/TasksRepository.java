package com.ycuwq.todo.data.source;

import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.di.DataSourceModule;
import com.ycuwq.todo.di.Local;
import com.ycuwq.todo.data.source.local.TasksDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Task的仓库，利用Dagger2 实现单例和依赖注入
 * 利用{@link DataSourceModule}
 * Created by 杨晨 on 2017/5/12.
 */
@Singleton
public class TasksRepository implements TasksDataSource {

	private TasksDataSource mLocalDataSource;

	@Inject
	public TasksRepository(@Local TasksDataSource localDataSource) {
		mLocalDataSource = localDataSource;
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
