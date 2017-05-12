package com.ycuwq.todo.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.ycuwq.todo.R;
import com.ycuwq.todo.app.BaseActivity;
import com.ycuwq.todo.common.util.ActivityUtils;

import javax.inject.Inject;

/**
 *
 * Created by 杨晨 on 2017/5/8.
 */
public class TaskActivity extends BaseActivity {

	private final String TAG = getClass().getSimpleName();
	@Inject
	TaskViewModel mTaskViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_task);
		initToolbar();

		TaskFragment taskFragment = findTaskFragment();

		DaggerTaskViewModelComponent.builder().taskRepositoryComponent(getApp().getRepositoryComponent())
				.build().inject(this);


	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	private TaskFragment findTaskFragment() {
		TaskFragment taskFragment = (TaskFragment) getSupportFragmentManager().
				findFragmentById(R.id.frame_task_content);
		if (taskFragment == null) {
			taskFragment = TaskFragment.newInstance();
			ActivityUtils.addFragmentToActivity(
					getSupportFragmentManager(), taskFragment, R.id.frame_task_content);
		}
		return taskFragment;
	}
}
