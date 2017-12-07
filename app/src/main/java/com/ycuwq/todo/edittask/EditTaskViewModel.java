package com.ycuwq.todo.edittask;

import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.source.local.AppDatabase;

import javax.inject.Inject;

/**
 * Created by yangchen on 2017/12/6.
 */
public class EditTaskViewModel extends BaseViewModel {

	private final AppDatabase mAppDatabase;

	@Inject
	public EditTaskViewModel(AppDatabase appDatabase) {
		mAppDatabase = appDatabase;
	}



}
