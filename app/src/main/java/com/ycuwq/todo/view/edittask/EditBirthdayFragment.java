package com.ycuwq.todo.view.edittask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycuwq.common.util.AutoClearedValue;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;
import com.ycuwq.todo.base.BaseFragment;
import com.ycuwq.todo.databinding.FragEditBirthdayBinding;
import com.ycuwq.todo.di.Injectable;

import javax.inject.Inject;

/**
 * {@link EditTaskFragment} 中的生日的部分
 * Created by yangchen on 2017/12/5.
 */
public class EditBirthdayFragment extends BaseFragment implements Injectable {

	private AutoClearedValue<FragEditBirthdayBinding> mBinding;
	private EditTaskViewModel mEditTaskViewModel;

	@Inject
	public EditBirthdayFragment() {
	}


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		FragEditBirthdayBinding binding = FragEditBirthdayBinding.inflate(inflater, container, false);
		mBinding = new AutoClearedValue<>(this, binding);
		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mBinding.get().setTask(mEditTaskViewModel.task);
		mBinding.get().setFragment(this);

	}

	public void setEditTaskViewModel(EditTaskViewModel editTaskViewModel) {
		mEditTaskViewModel = editTaskViewModel;
	}

	public void chooseStartDate(View v) {
		DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();

		datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
			@Override
			public void onDateChoose(int year, int month, int day) {
				mEditTaskViewModel.setStartDate(year, month, day);
			}
		});
        datePickerDialogFragment.show(getChildFragmentManager(), "chooseStartDate");

	}

}
