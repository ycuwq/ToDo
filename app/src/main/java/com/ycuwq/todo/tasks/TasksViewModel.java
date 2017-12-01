package com.ycuwq.todo.tasks;

import android.support.annotation.NonNull;

import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.source.TasksRepository;

import javax.inject.Inject;

/**
 * Task的ViewModel 利用Dagger2注入TaskRepository
 * Created by 杨晨 on 2017/5/9.
 */
public class TasksViewModel extends BaseViewModel {
	private final String TAG = getClass().getSimpleName();
	private final TasksRepository mTasksRepository;

	@Inject
	public TasksViewModel(@NonNull TasksRepository tasksRepository) {
		mTasksRepository = tasksRepository;

	}

	String getSnakeBarText() {
		return snakeBarText.get();
	}
}
