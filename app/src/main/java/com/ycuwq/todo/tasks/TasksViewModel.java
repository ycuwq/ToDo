package com.ycuwq.todo.tasks;

import android.support.annotation.NonNull;

import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.source.local.AppDatabase;

import javax.inject.Inject;

/**
 * Task的ViewModel
 * Created by 杨晨 on 2017/5/9.
 */
public class TasksViewModel extends BaseViewModel {
	private final String TAG = getClass().getSimpleName();
	private final AppDatabase mAppDatabase;

	@Inject
	public TasksViewModel(@NonNull AppDatabase appDatabase) {
		mAppDatabase = appDatabase;

	}


}
