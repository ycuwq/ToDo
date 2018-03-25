package com.ycuwq.todo.view.edittask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.ycuwq.calendarview.Date;
import com.ycuwq.common.util.ActivityUtils;
import com.ycuwq.common.util.DateUtil;
import com.ycuwq.todo.R;
import com.ycuwq.todo.base.BaseActivity;
import com.ycuwq.todo.data.bean.Task;

import org.joda.time.DateTime;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 新建或者编辑任务
 * Created by yangchen on 2017/12/1.
 */
public class EditTaskActivity extends BaseActivity implements HasSupportFragmentInjector {

	public static final String KEY_EDIT_TASK = "KEY_EDIT_TASK";

	@Inject
	DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
	@Inject
	Lazy<EditTaskFragment> fragmentProvider;

	public static Intent getIntent(@NonNull Context context, @Nullable Task task) {
		Intent intent = new Intent(context, EditTaskActivity.class);
	    intent.putExtra(KEY_EDIT_TASK, task);
		return intent;
	}

    public static Intent getIntent(@NonNull Context context, @NonNull Date date) {
	    Task task = new Task();
        task.setType(Task.TYPE_SCHEDULE);
        DateTime dateTime = new DateTime(date.getYear(), date.getMonth(), date.getDay(), 9, 0);
        if (dateTime.isBeforeNow()) {
            dateTime = new DateTime();
        }
        task.setStartTime(new java.util.Date(dateTime.getMillis()));
        task.setYear(dateTime.getYear());
        task.setMonth(dateTime.getMonthOfYear());
        task.setDay(dateTime.getDayOfMonth());
        task.setStartDate(DateUtil.getDateString(task.getYear(), task.getMonth(), task.getDay()));
        return getIntent(context, task);
    }

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_edit_task);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		showFragment();
	}

	private void showFragment() {
		EditTaskFragment fragment = (EditTaskFragment) getSupportFragmentManager().
				findFragmentById(R.id.frame_content);
		if (fragment == null) {
			fragment = fragmentProvider.get();
			ActivityUtils.addFragmentToActivity(
					getSupportFragmentManager(), fragment, R.id.frame_content);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public AndroidInjector<Fragment> supportFragmentInjector() {
		return dispatchingAndroidInjector;
	}
}
