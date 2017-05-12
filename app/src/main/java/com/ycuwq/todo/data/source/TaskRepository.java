package com.ycuwq.todo.data.source;

import com.ycuwq.todo.data.bean.Task;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Task的仓库，利用Dagger2 实现单例和依赖注入
 * 利用{@link DataSourceModule}和
 * Created by 杨晨 on 2017/5/12.
 */
@Singleton
public class TaskRepository implements TaskDataSource{

	private TaskDataSource mLocalDataSource;

	@Inject
	public TaskRepository(@Local TaskDataSource localDataSource) {
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
