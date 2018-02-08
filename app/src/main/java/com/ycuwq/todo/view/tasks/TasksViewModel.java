package com.ycuwq.todo.view.tasks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.text.TextUtils;

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
	private final LiveData<List<Task>> mTasks;
	private final MutableLiveData<String> mDate;
	@Inject
	public TasksViewModel(TaskRepository taskRepository) {
	    mTaskRepository = taskRepository;
        mDate = new MutableLiveData<>();
        mTasks = Transformations.switchMap(mDate, input -> {
           if (TextUtils.isEmpty(input)) {
               return mTaskRepository.loadAllTask();
           }
           return mTaskRepository.loadTasks(input);
        });
    }

    public LiveData<List<Task>> getTasks() {
        return mTasks;
    }

    public MutableLiveData<String> getDate() {
        return mDate;
    }

    public void onTaskClicked() {


	}
}
