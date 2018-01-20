package com.ycuwq.todo.view.edittask;

import com.ycuwq.common.util.DateUtil;
import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.AppDb;

import javax.inject.Inject;

/**
 * Created by yangchen on 2017/12/6.
 */
public class EditTaskViewModel extends BaseViewModel {

	private final AppDb mAppDb;

	Task task;

	@Inject
	public EditTaskViewModel(AppDb appDb) {
		mAppDb = appDb;
		task = new Task();
	}

	public void setStartDate(int year, int month, int day) {
	    task.setStartDate(DateUtil.getDateString(year, month, day));
    }

    public void save() {

    }


}
