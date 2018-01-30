package com.ycuwq.todo.view.edittask;

import android.support.v4.app.Fragment;
import android.view.View;

import com.ycuwq.common.util.DateUtil;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;
import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.AppDb;
import com.ycuwq.todo.view.common.ChooseRemindTimeDialogFragment;
import com.ycuwq.todo.view.edittask.child.RepeatModeDialogFragment;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by yangchen on 2017/12/6.
 */
public class EditTaskViewModel extends BaseViewModel {

	private final AppDb mAppDb;

	private Task mTask;

	private Fragment mBaseFragment;

	@Inject
	public EditTaskViewModel(AppDb appDb) {
		mAppDb = appDb;
		mTask = new Task();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        setStartDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        mTask.setReminderTime(calendar.getTime());
	}



	public void setStartDate(int year, int month, int day) {
	    mTask.setStartDate(DateUtil.getDateString(year, month - 1, day));
    }

    public void save() {

    }

    public Task getTask() {
        return mTask;
    }

    public void setTask(Task task) {
        this.mTask = task;
    }

    public Fragment getBaseFragment() {
        return mBaseFragment;
    }

    public void setBaseFragment(Fragment baseFragment) {
        mBaseFragment = baseFragment;
    }

    public void chooseStartDate(View v) {
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();

        datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
            @Override
            public void onDateChoose(int year, int month, int day) {
                setStartDate(year, month, day);
            }
        });
        datePickerDialogFragment.show(mBaseFragment.getChildFragmentManager(), "chooseStartDate");

    }

    public void chooseRemindTime(View v) {
        ChooseRemindTimeDialogFragment chooseRemindTimeDialogFragment = new ChooseRemindTimeDialogFragment();
        chooseRemindTimeDialogFragment.show(mBaseFragment.getChildFragmentManager(), "chooseRemindTime");
    }

    public void chooseRepeatMode(View v) {
        RepeatModeDialogFragment repeatModeDialogFragment = new RepeatModeDialogFragment();
        repeatModeDialogFragment.setOnRepeatModeSelectedListener(new RepeatModeDialogFragment.OnRepeatModeSelectedListener() {
            @Override
            public void onRepeatModeSelected(int mode) {
                mTask.setRepeat(mode);
            }
        });
        repeatModeDialogFragment.show(mBaseFragment.getChildFragmentManager(), "chooseRepeatMode");
    }
}
