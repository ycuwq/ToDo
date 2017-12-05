package com.ycuwq.todo.addedittask;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.ycuwq.common.util.ActivityUtils;
import com.ycuwq.todo.R;
import com.ycuwq.todo.base.BaseFragment;
import com.ycuwq.todo.databinding.FragEditTaskBinding;
import com.ycuwq.todo.di.Injectable;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * 在{@link EditTaskActivity} 中使用
 * Created by yangchen on 2017/12/1.
 */
public class EditTaskFragment extends BaseFragment implements Injectable {

	private final static int RADIO_BUTTON_DRAWABLE_SIZE = 120;

	private FragEditTaskBinding mBinding;

	@Inject
	Lazy<EditScheduleFragment> mEditScheduleFragmentProvider;

	@Inject
	Lazy<EditBirthdayFragment> mBirthdayFragmentProvider;

	@Inject
	Lazy<EditAnniversaryFragment> mAnniversaryFragmentProvider;

	@Inject
	public EditTaskFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = FragEditTaskBinding.inflate(inflater, container, false);
		mBinding.setFragment(this);
		return mBinding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		showScheduleFragment();
		initView();
	}

	private void initView() {
		setRadioButtonDrawableSize();
		mBinding.radioGroupChooseType.setOnCheckedChangeListener((group, checkedId) -> {
			switch (checkedId) {
				case R.id.radioButton_schedule:
					showScheduleFragment();
					break;
				case R.id.radioButton_birthday:
					showBirthdayFragment();
					break;
				case R.id.radioButton_anniversary:
					showAnniversaryFragment();
					break;
				default:
					break;
			}
		});
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
	}

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


}
