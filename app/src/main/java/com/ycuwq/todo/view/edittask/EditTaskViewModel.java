package com.ycuwq.todo.view.edittask;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.ycuwq.common.util.DateUtil;
import com.ycuwq.todo.R;
import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.AppDb;
import com.ycuwq.todo.view.edittask.child.ChooseDateDialogFragment;
import com.ycuwq.todo.view.edittask.child.ChooseRemindTimeDialogFragment;
import com.ycuwq.todo.view.edittask.child.RepeatModeDialogFragment;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * 编辑日程的ViewModel
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
        mTask.setType(Task.TYPE_SCHEDULE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);

        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        mTask.setReminderTime(calendar.getTime());
        setStartDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE));
    }

    public void setTaskType(@Task.TaskType int type) {
	    mTask.setType(type);
    }

	public void setStartDate(int year, int month, int day) {
        mTask.setYear(year);
        mTask.setMonth(month);
        mTask.setDay(day);
	    mTask.setStartDate(DateUtil.getDateString(year, month - 1, day));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTask.getReminderTime());
        calendar.set(year, month - 1, day);
        mTask.setReminderTime(calendar.getTime());
    }

    public boolean save() {
	    if (TextUtils.isEmpty(mTask.getName())) {
	        switch (mTask.getType()) {
                case Task.TYPE_ANNIVERSARY:
                    snackbarText.set(mBaseFragment.getString(R.string.hint_input_anniversary_content));
                    break;
                case Task.TYPE_BIRTHDAY:
                    snackbarText.set(mBaseFragment.getString(R.string.hint_input_name));
                    break;
                case Task.TYPE_SCHEDULE:
                    snackbarText.set(mBaseFragment.getString(R.string.hint_input_schedule_name));
                    break;
                default:
                    break;
            }
            return false;
        }
        if (mTask.getType() != Task.TYPE_SCHEDULE) {
            mTask.setRepeat(Task.REPEAT_YEAR);
        }
        // TODO: 2018/2/4 修改线程调用方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAppDb.taskDao().insertTask(mTask);

            }
        }).start();
        return true;
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
        ChooseDateDialogFragment chooseDateDialogFragment = new ChooseDateDialogFragment();
        chooseDateDialogFragment.setOnDateSelectedListener(this::setStartDate);
        chooseDateDialogFragment.setInitDate(mTask.getYear(), mTask.getMonth(), mTask.getDay());
        chooseDateDialogFragment.show(mBaseFragment.getChildFragmentManager(), "chooseStartDate");

    }

    public void chooseRemindTime(View v) {
        ChooseRemindTimeDialogFragment chooseRemindTimeDialogFragment = new ChooseRemindTimeDialogFragment();
        chooseRemindTimeDialogFragment.setDate(mTask.getStartDate());
        chooseRemindTimeDialogFragment.setOnTimeSelectedListener((hour, minute) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(mTask.getYear(), mTask.getMonth() - 1, mTask.getDay(), hour, minute, 0);
            mTask.setReminderTime(calendar.getTime());
        });
        chooseRemindTimeDialogFragment.show(mBaseFragment.getChildFragmentManager(), "chooseRemindTime");
    }

    public void chooseRepeatMode(View v) {
        RepeatModeDialogFragment repeatModeDialogFragment = new RepeatModeDialogFragment();
        repeatModeDialogFragment.setOnRepeatModeSelectedListener(mode -> mTask.setRepeat(mode));
        repeatModeDialogFragment.show(mBaseFragment.getChildFragmentManager(), "chooseRepeatMode");
    }
}
