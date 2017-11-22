package com.ycuwq.todo.task;

import android.support.annotation.NonNull;

import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.source.TaskRepository;

import javax.inject.Inject;

/**
 * Task的ViewModel 利用Dagger2注入TaskRepository
 * Created by 杨晨 on 2017/5/9.
 */
public class TaskViewModel extends BaseViewModel {

	private final TaskRepository mTaskRepository;

	@Inject
	public TaskViewModel(@NonNull TaskRepository taskRepository) {
		mTaskRepository = taskRepository;

	}

	String getSnakeBarText() {
		return snakeBarText.get();
	}
}
