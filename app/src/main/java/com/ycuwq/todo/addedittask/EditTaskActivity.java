package com.ycuwq.todo.addedittask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.ycuwq.common.util.ActivityUtils;
import com.ycuwq.todo.R;
import com.ycuwq.todo.base.BaseActivity;
import com.ycuwq.todo.data.bean.Task;

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

	@Inject
	DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
	@Inject
	Lazy<EditTaskFragment> fragmentProvider;

	public static Intent getIntent(@NonNull Context context, @Nullable Task task) {
		Intent intent = new Intent(context, EditTaskActivity.class);
		if (task != null) {
			// TODO: 2017/12/1 Task不为空，编辑的情况
		}
		return intent;
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
