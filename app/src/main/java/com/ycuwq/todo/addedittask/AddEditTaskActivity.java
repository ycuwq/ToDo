package com.ycuwq.todo.addedittask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ycuwq.todo.R;
import com.ycuwq.todo.base.BaseActivity;
import com.ycuwq.todo.data.bean.Task;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 新建或者编辑任务
 *
 * Created by yangchen on 2017/12/1.
 */
public class AddEditTaskActivity extends BaseActivity implements HasSupportFragmentInjector {

	@Inject
	DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;


	public static Intent getIntent(@NonNull Context context, @Nullable Task task) {
		Intent intent = new Intent(context, AddEditTaskActivity.class);
		if (task != null) {
			// TODO: 2017/12/1 Task不为空，编辑的情况
		}
		return intent;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_frag);
	}

	@Override
	public AndroidInjector<Fragment> supportFragmentInjector() {
		return dispatchingAndroidInjector;
	}
}
