package com.ycuwq.todo.tasks;

import android.support.annotation.NonNull;

import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.AppDb;

import javax.inject.Inject;

/**
 * Task的ViewModel
 * Created by 杨晨 on 2017/5/9.
 */
public class TasksViewModel extends BaseViewModel {
	private final String TAG = getClass().getSimpleName();
	private final AppDb mAppDb;

	@Inject
	public TasksViewModel(@NonNull AppDb appDb) {
		mAppDb = appDb;
	}

	public void onTaskClicked() {
		Task task = new Task();
		task.setName("hahaha");
		task.setAllDay(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				mAppDb.taskDao().insertTask(task);

			}
		}).start();

	}


}
