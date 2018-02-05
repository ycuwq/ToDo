package com.ycuwq.todo.view.tasks;

import android.arch.lifecycle.LiveData;

import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.TaskRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Task的ViewModel
 * Created by 杨晨 on 2017/5/9.
 */
public class TasksViewModel extends BaseViewModel {
	private final String TAG = getClass().getSimpleName();
	private final TaskRepository mTaskRepository;

	@Inject
	public TasksViewModel(TaskRepository taskRepository) {
	    mTaskRepository = taskRepository;
	}

	public void onTaskClicked() {


	}

	public LiveData<List<Task>> loadAllTask() {
	    return mTaskRepository.loadAllTask();
    }


}
