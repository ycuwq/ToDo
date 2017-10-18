package com.ycuwq.todo.task;

import android.databinding.ObservableField;

import com.ycuwq.todo.app.BaseViewModel;
import com.ycuwq.todo.data.source.TaskRepository;

import javax.inject.Inject;

/**
 * Task的ViewModel 利用Dagger2注入TaskRepository
 * Created by 杨晨 on 2017/5/9.
 */
public class TaskViewModel extends BaseViewModel {

	private TaskRepository mTaskRepository;
	public ObservableField<String> snakeBarText = new ObservableField<>();

	@Inject
	public TaskViewModel(TaskRepository taskRepository) {
		mTaskRepository = taskRepository;
	}

	public String getSnakeBarText() {
		return snakeBarText.get();
	}
}
