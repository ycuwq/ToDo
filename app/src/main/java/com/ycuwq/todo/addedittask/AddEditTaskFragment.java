package com.ycuwq.todo.addedittask;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.ycuwq.todo.R;
import com.ycuwq.todo.base.BaseFragment;
import com.ycuwq.todo.databinding.FragAddTaskBinding;
import com.ycuwq.todo.di.Injectable;

import javax.inject.Inject;

/**
 * 在{@link AddEditTaskActivity} 中使用
 * Created by yangchen on 2017/12/1.
 */
public class AddEditTaskFragment extends BaseFragment implements Injectable {

	private final static int RADIO_BUTTON_DRAWABLE_SIZE = 120;

	private FragAddTaskBinding mBinding;

	@Inject
	public AddEditTaskFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = FragAddTaskBinding.inflate(inflater, container, false);
		mBinding.setFragment(this);
		return mBinding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		setRadioButtonDrawableSize();
		mBinding.radioGroupChooseType.setOnCheckedChangeListener((group, checkedId) -> {
			switch (checkedId) {
				case R.id.radioButton_schedule:
					break;
				case R.id.radioButton_birthday:
					break;
				case R.id.radioButton_anniversary:
					break;
				default:
					break;
			}
		});
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
