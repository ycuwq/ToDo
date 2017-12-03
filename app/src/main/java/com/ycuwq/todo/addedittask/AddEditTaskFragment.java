package com.ycuwq.todo.addedittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycuwq.todo.base.BaseFragment;
import com.ycuwq.todo.databinding.FragAddTaskBinding;
import com.ycuwq.todo.di.Injectable;

import javax.inject.Inject;

/**
 * 在{@link AddEditTaskActivity} 中使用
 * Created by yangchen on 2017/12/1.
 */
public class AddEditTaskFragment extends BaseFragment implements Injectable {

	private FragAddTaskBinding mBinding;

	@Inject
	public AddEditTaskFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = FragAddTaskBinding.inflate(inflater, container, false);
		return mBinding.getRoot();
	}


}
