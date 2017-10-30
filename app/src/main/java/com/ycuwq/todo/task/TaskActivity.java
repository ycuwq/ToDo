package com.ycuwq.todo.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

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

		DaggerTaskViewModelComponent.builder().taskRepositoryComponent(getApp().getRepositoryComponent())
				.build().inject(this);
		TaskFragment taskFragment = findTaskFragment();
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

	private TaskFragment findTaskFragment() {
		TaskFragment taskFragment = (TaskFragment) getSupportFragmentManager().
				findFragmentById(R.id.frame_task_content);
		if (taskFragment == null) {
			taskFragment = TaskFragment.newInstance(mTaskViewModel);
			ActivityUtils.addFragmentToActivity(
					getSupportFragmentManager(), taskFragment, R.id.frame_task_content);
		} else {
			taskFragment.setViewModel(mTaskViewModel);
		}
		return taskFragment;
	}
}
