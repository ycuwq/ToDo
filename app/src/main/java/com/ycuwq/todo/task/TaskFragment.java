package com.ycuwq.todo.task;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangchen.extracalendarview.ExtraCalendarView;
import com.ycuwq.todo.app.BaseFragment;
import com.ycuwq.todo.common.util.SnakeBarUtil;
import com.ycuwq.todo.databinding.FragTaskBinding;

/**
 * Created by 杨晨 on 2017/5/10.
 */
public class TaskFragment extends BaseFragment{

	private final String TAG = getClass().getSimpleName();

	private FragTaskBinding mBinding;

	private TaskViewModel mViewModel;
	private ExtraCalendarView extraCalendarView;
	private Observable.OnPropertyChangedCallback mSnakeBarCallback;
	public TaskFragment() {}

	public static TaskFragment newInstance(TaskViewModel taskViewModel) {
		TaskFragment taskFragment = new TaskFragment();
		taskFragment.setViewModel(taskViewModel);
		return taskFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		mBinding = FragTaskBinding.inflate(inflater, container, false);
		extraCalendarView = mBinding.extraCalendarView;
		extraCalendarView.setStartDate(2017,5, 15);
//
		setupSnakeBar();
		mBinding.button.setOnClickListener((v) ->
				extraCalendarView.setCurrentMonth(2017, 12));
		return mBinding.getRoot();

	}

	@Override
	public void onResume() {
		super.onResume();
//		extraCalendarView.setCurrentMonth(2017, 7);
	}

	public void setViewModel(TaskViewModel mViewModel) {
		this.mViewModel = mViewModel;
	}

	/**
	 * 设置SnakeBar的监听，当mViewModel的snakeBarText发生改变时SnakeBar显示
	 */
	private void setupSnakeBar() {
		mSnakeBarCallback = new Observable.OnPropertyChangedCallback() {
			@Override
			public void onPropertyChanged(Observable observable, int i) {
				SnakeBarUtil.showSnackBar(getView(), mViewModel.getSnakeBarText());
			}
		};
		if (mViewModel == null) {
			return;
		}
		mViewModel.snakeBarText.addOnPropertyChangedCallback(mSnakeBarCallback);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mViewModel.snakeBarText.removeOnPropertyChangedCallback(mSnakeBarCallback);
	}
}
