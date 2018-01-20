package com.ycuwq.todo.view.tasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.ycuwq.common.util.ActivityUtils;
import com.ycuwq.todo.R;
import com.ycuwq.todo.base.BaseActivity;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 显示任务总览的Activity
 * Created by 杨晨 on 2017/5/8.
 */
public class TasksActivity extends BaseActivity implements HasSupportFragmentInjector {

	private final String TAG = getClass().getSimpleName();

	@Inject
	Lazy<TasksFragment> taskFragmentProvider;
	@Inject
	DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_tasks);
		initToolbar();

		findTaskFragment();
	}

	private void initToolbar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.task, menu);
		return true;
	}

	private TasksFragment findTaskFragment() {
		TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager().
				findFragmentById(R.id.frame_task_content);
		if (tasksFragment == null) {
			tasksFragment = taskFragmentProvider.get();
			ActivityUtils.addFragmentToActivity(
					getSupportFragmentManager(), tasksFragment, R.id.frame_task_content);
		}
		return tasksFragment;
	}

	@Override
	public AndroidInjector<Fragment> supportFragmentInjector() {
		return dispatchingAndroidInjector;
	}
}
