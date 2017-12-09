package com.ycuwq.todo.edittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycuwq.todo.base.BaseFragment;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.databinding.FragEditAnniversaryBinding;
import com.ycuwq.todo.di.Injectable;

import javax.inject.Inject;

/**
 * {@link EditTaskFragment} 中的纪念日的部分
 * Created by yangchen on 2017/12/5.
 */
public class EditAnniversaryFragment extends BaseFragment implements Injectable {

	private FragEditAnniversaryBinding mBinding;
	private EditTaskViewModel mEditTaskViewModel;
	private Task mTask;
	@Inject
	public EditAnniversaryFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = FragEditAnniversaryBinding.inflate(inflater, container, false);
		return mBinding.getRoot();
	}


	public void setEditTaskViewModel(EditTaskViewModel editTaskViewModel) {
		mEditTaskViewModel = editTaskViewModel;
	}
}
