package com.ycuwq.todo.edittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ycuwq.todo.base.BaseFragment;
import com.ycuwq.todo.databinding.FragEditBirthdayBinding;
import com.ycuwq.todo.di.Injectable;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * {@link EditTaskFragment} 中的生日的部分
 * Created by yangchen on 2017/12/5.
 */
public class EditBirthdayFragment extends BaseFragment implements Injectable {

	private FragEditBirthdayBinding mBinding;
	private EditTaskViewModel mEditTaskViewModel;

	@Inject
	public EditBirthdayFragment() {
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = FragEditBirthdayBinding.inflate(inflater, container, false);
		Log.d(TAG, "onCreateView: " + mEditTaskViewModel);
		return mBinding.getRoot();
	}


	@Inject
	public void setEditTaskViewModel(EditTaskViewModel editTaskViewModel) {
		mEditTaskViewModel = editTaskViewModel;
	}

	public void chooseBirthday() {
		Toast.makeText(getContext(), "chooseBirthday", Toast.LENGTH_SHORT).show();
	}

}
