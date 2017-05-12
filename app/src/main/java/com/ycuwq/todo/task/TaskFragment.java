package com.ycuwq.todo.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycuwq.todo.app.BaseFragment;
import com.ycuwq.todo.databinding.FragTaskBinding;

/**
 * Created by 杨晨 on 2017/5/10.
 */
public class TaskFragment extends BaseFragment{

	private FragTaskBinding mBinding;

	public TaskFragment() {}

	public static TaskFragment newInstance() {
		return new TaskFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = FragTaskBinding.inflate(inflater, container, false);
		return mBinding.getRoot();

	}
}
