package com.ycuwq.todo.view.edittask;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.ycuwq.common.util.ActivityUtils;
import com.ycuwq.todo.R;
import com.ycuwq.todo.base.ViewModelFragment;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.databinding.FragEditTaskBinding;
import com.ycuwq.todo.di.Injectable;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * 在{@link EditTaskActivity} 中使用
 * Created by yangchen on 2017/12/1.
 */
public class EditTaskFragment extends ViewModelFragment implements Injectable, View.OnClickListener {

	private final static int RADIO_BUTTON_DRAWABLE_SIZE = 100;

	private FragEditTaskBinding mBinding;

	@Inject
	Lazy<EditScheduleFragment> mEditScheduleFragmentProvider;

	@Inject
	Lazy<EditBirthdayFragment> mBirthdayFragmentProvider;

	@Inject
	Lazy<EditAnniversaryFragment> mAnniversaryFragmentProvider;

	@Inject
	ViewModelProvider.Factory mViewModelFactory;

	EditTaskViewModel mEditTaskViewModel;

	@Inject
	public EditTaskFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = FragEditTaskBinding.inflate(inflater, container, false);
		mBinding.setFragment(this);
        mEditTaskViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EditTaskViewModel.class);
        mEditTaskViewModel.setBaseFragment(this);
        Task task = getActivity().getIntent().getParcelableExtra(EditTaskActivity.KEY_EDIT_TASK);
        if (task != null) {
            mEditTaskViewModel.setTask(task);
        }
        registerSnackbarText(mEditTaskViewModel);
        initView();
        return mBinding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		showChildFragment();
	}

	private void initView() {
		setRadioButtonDrawableSize();
		mBinding.radioGroupChooseType.setOnCheckedChangeListener((group, checkedId) -> {
			switch (checkedId) {
				case R.id.radioButton_schedule:
				    mEditTaskViewModel.setTaskType(Task.TYPE_SCHEDULE);
					showScheduleFragment();
					break;
				case R.id.radioButton_birthday:
                    mEditTaskViewModel.setTaskType(Task.TYPE_BIRTHDAY);
					showBirthdayFragment();
					break;
				case R.id.radioButton_anniversary:
                    mEditTaskViewModel.setTaskType(Task.TYPE_ANNIVERSARY);
					showAnniversaryFragment();
					break;
				default:
					break;
			}
		});

		if (getActivity() != null) {
            FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_task);
            fab.setOnClickListener(this);
        }
	}

	private void showChildFragment() {
	    switch (mEditTaskViewModel.getTask().getType()) {
            case Task.TYPE_SCHEDULE:
                showScheduleFragment();
                break;
            case Task.TYPE_ANNIVERSARY:
                showAnniversaryFragment();
                break;
            case Task.TYPE_BIRTHDAY:
                showBirthdayFragment();
                break;
            default:
                showScheduleFragment();
                break;

        }
    }

	private void showScheduleFragment() {
		Fragment fragment = getChildFragmentManager().
				findFragmentById(R.id.content);

		if (fragment == null) {
			fragment = mEditScheduleFragmentProvider.get();
			ActivityUtils.addFragmentToActivity(
					getChildFragmentManager(), fragment, R.id.content);
		} else if (!(fragment instanceof EditScheduleFragment)) {
			fragment = mEditScheduleFragmentProvider.get();
			ActivityUtils.replaceFragmentToActivity(
					getChildFragmentManager(), fragment, R.id.content);
		}
		mEditScheduleFragmentProvider.get().setEditTaskViewModel(mEditTaskViewModel);
	}

	private void showBirthdayFragment() {
		Fragment fragment = getChildFragmentManager().
				findFragmentById(R.id.content);
		if (fragment == null) {
			fragment = mBirthdayFragmentProvider.get();
			ActivityUtils.addFragmentToActivity(
					getChildFragmentManager(), fragment, R.id.content);
		} else if (!(fragment instanceof EditBirthdayFragment)) {
			fragment = mBirthdayFragmentProvider.get();
			ActivityUtils.replaceFragmentToActivity(
					getChildFragmentManager(), fragment, R.id.content);
		}
		mBirthdayFragmentProvider.get().setEditTaskViewModel(mEditTaskViewModel);
	}

	private void showAnniversaryFragment() {
		Fragment fragment = getChildFragmentManager().
				findFragmentById(R.id.content);
		if (fragment == null) {
			fragment = mAnniversaryFragmentProvider.get();
			ActivityUtils.addFragmentToActivity(
					getChildFragmentManager(), fragment, R.id.content);
		} else if (!(fragment instanceof EditAnniversaryFragment)) {
			fragment = mAnniversaryFragmentProvider.get();
			ActivityUtils.replaceFragmentToActivity(
					getChildFragmentManager(), fragment, R.id.content);
		}
		mAnniversaryFragmentProvider.get().setEditTaskViewModel(mEditTaskViewModel);
	}

	/**
	 * RadioButton 可以设置Drawable但是不能在XML中设置大小。
	 */
	private void setRadioButtonDrawableSize() {
		RadioButton scheduleRb = mBinding.radioButtonSchedule;
		Drawable drawableSchedule = getResources().getDrawable(R.drawable.selector_btn_schedule);
		drawableSchedule.setBounds(0, 0, RADIO_BUTTON_DRAWABLE_SIZE, RADIO_BUTTON_DRAWABLE_SIZE);
		scheduleRb.setCompoundDrawables(null, drawableSchedule, null, null);

		RadioButton birthdayRb = mBinding.radioButtonBirthday;
		Drawable drawableDrawable = getResources().getDrawable(R.drawable.selector_btn_birthday);
		drawableDrawable.setBounds(0, 0, RADIO_BUTTON_DRAWABLE_SIZE, RADIO_BUTTON_DRAWABLE_SIZE);
		birthdayRb.setCompoundDrawables(null, drawableDrawable, null, null);

		RadioButton anniversaryRb = mBinding.radioButtonAnniversary;
		Drawable drawableAnniversary = getResources().getDrawable(R.drawable.selector_btn_anniversary);
		drawableAnniversary.setBounds(0, 0, RADIO_BUTTON_DRAWABLE_SIZE, RADIO_BUTTON_DRAWABLE_SIZE);
		anniversaryRb.setCompoundDrawables(null, drawableAnniversary, null, null);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fab_add_task:
			    boolean isFinish = mEditTaskViewModel.save();
			    if (isFinish && getActivity() != null) {
			        getActivity().finish();
                }
				break;
			default:
				break;
		}
	}
}
