package com.ycuwq.todo.addedittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycuwq.todo.base.BaseFragment;
import com.ycuwq.todo.databinding.FragEditScheduleBinding;
import com.ycuwq.todo.di.Injectable;

import javax.inject.Inject;

/**
 * {@link EditTaskFragment} 中的日程的的部分。
 * Created by yangchen on 2017/12/5.
 */
public class EditScheduleFragment extends BaseFragment implements Injectable {

	private FragEditScheduleBinding mBinding;


	@Inject
	public EditScheduleFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = FragEditScheduleBinding.inflate(inflater, container, false);
		return mBinding.getRoot();
	}
}
