package com.ycuwq.todo.task;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ycuwq.todo.R;
import com.ycuwq.todo.app.BaseActivity;

/**
 *
 * Created by 杨晨 on 2017/5/8.
 */
public class TaskActivity extends BaseActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_task);
	}
}
