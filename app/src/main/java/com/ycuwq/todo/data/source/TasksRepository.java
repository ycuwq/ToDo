package com.ycuwq.todo.data.source;

import android.support.annotation.NonNull;

import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.TasksDataSource;
import com.ycuwq.todo.data.source.local.TasksLocalDataSource;

import java.util.List;

/**
 * Task的仓库，利用Dagger2 实现单例和依赖注入
 * Created by 杨晨 on 2017/5/12.
 */
public class TasksRepository {

	private TasksDataSource mLocalDataSource;

	public TasksRepository(@NonNull TasksLocalDataSource localDataSource) {
		mLocalDataSource = localDataSource;
	}

	public void saveTask(Task task) {

	}

	public void saveTasks(List<Task> tasks) {

	}

	public void getTasks(TasksDataSource.GetTasksCallback callback) {

	}

	public void updateTask(Task task) {

	}

	public void deleteTask(long id) {

	}

	public void deleteTasks(List<Task> list) {

	}

	public void deleteAll() {

	}
}
