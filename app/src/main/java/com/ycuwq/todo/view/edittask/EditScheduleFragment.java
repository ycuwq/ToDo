package com.ycuwq.todo.view.edittask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycuwq.common.util.AutoClearedValue;
import com.ycuwq.todo.BR;
import com.ycuwq.todo.base.ViewModelFragment;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.databinding.FragEditScheduleBinding;
import com.ycuwq.todo.di.Injectable;

import javax.inject.Inject;

/**
 * {@link EditTaskFragment} 中的日程的的部分。
 * Created by yangchen on 2017/12/5.
 */
public class EditScheduleFragment extends ViewModelFragment implements Injectable {

	private AutoClearedValue<FragEditScheduleBinding> mBinding;
	private EditTaskViewModel mEditTaskViewModel;
	private Task mTask;
	@Inject
	public EditScheduleFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		FragEditScheduleBinding binding = FragEditScheduleBinding.inflate(inflater, container, false);
		mBinding = new AutoClearedValue<>(this, binding);

		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (mTask == null) {
			mTask = new Task();
		}
		mBinding.get().setVariable(BR.viewModel, mEditTaskViewModel);
		mBinding.get().setFragment(this);
		initView();

	}


	private void initView() {

	}

	public void setEditTaskViewModel(EditTaskViewModel editTaskViewModel) {
		// TODO: 2017/12/9 这里依赖注入，如何用Dagger去处理？
		mEditTaskViewModel = editTaskViewModel;
	}
}
