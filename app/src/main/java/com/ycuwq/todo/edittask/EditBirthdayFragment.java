package com.ycuwq.todo.edittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ycuwq.common.util.AutoClearedValue;
import com.ycuwq.todo.base.BaseFragment;
import com.ycuwq.todo.data.bean.Task;
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
	private Task mTask;
	@Inject
	public EditBirthdayFragment() {
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		FragEditBirthdayBinding binding = FragEditBirthdayBinding.inflate(inflater, container, false);
		mBinding = new AutoClearedValue<>(this, binding);
		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (mTask == null) {
			mTask = new Task();
		}
		mBinding.get().setTask(mTask);
	}

	public void setEditTaskViewModel(EditTaskViewModel editTaskViewModel) {
		mEditTaskViewModel = editTaskViewModel;
	}

	public void chooseBirthday() {
		Toast.makeText(getContext(), "chooseBirthday", Toast.LENGTH_SHORT).show();
	}

}
