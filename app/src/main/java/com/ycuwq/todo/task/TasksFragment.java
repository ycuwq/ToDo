package com.ycuwq.todo.task;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangchen.extracalendarview.ExtraCalendarView;
import com.yangchen.extracalendarview.base.Date;
import com.yangchen.extracalendarview.listener.OnDayClickListener;
import com.ycuwq.todo.R;
import com.ycuwq.todo.base.BaseFragment;
import com.ycuwq.common.recycler.ExRecyclerAdapter;
import com.ycuwq.common.recycler.ExRecyclerViewHolder;
import com.ycuwq.common.util.SnakeBarUtil;
import com.ycuwq.todo.databinding.FragTaskBinding;
import com.ycuwq.todo.di.Injectable;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by 杨晨 on 2017/5/10.
 */
public class TasksFragment extends BaseFragment implements Injectable {

	private final String TAG = getClass().getSimpleName();

	private FragTaskBinding mBinding;

	private	TasksViewModel mViewModel;

	@Inject
	ViewModelProvider.Factory mViewModelFactory;

	private ExtraCalendarView mExtraCalendarView;
	private Observable.OnPropertyChangedCallback mSnakeBarCallback;

	@Inject
	public TasksFragment() {}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TasksViewModel.class);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		mBinding = FragTaskBinding.inflate(inflater, container, false);
		mExtraCalendarView = mBinding.extraCalendarView;
		mExtraCalendarView.setOnDayClickListener(new OnDayClickListener() {
			@Override
			public void onClick(View v, Date date) {
				Log.d(TAG, "onClick: " + date.toString());
			}
		});
//		mExtraCalendarView.setCalendarType(ExtraCalendarView.CALENDAR_TYPE_WEEK);
		setupSnakeBar();
		initView();
		return mBinding.getRoot();
	}

	private void initView() {
		RecyclerView recyclerView = mBinding.recyclerView;
		ExRecyclerAdapter<String> adapter = new ExRecyclerAdapter<String>(getContext(), R.layout.item_choose) {
			@Override
			public void bindData(ExRecyclerViewHolder holder, String s, int position) {
				TextView textView = holder.getView(R.id.text);
				textView.setText(s);
				textView.setOnClickListener(v -> {
					mExtraCalendarView.setCurrentDate(2017, 10, 1, true);
				});
			}
		};
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add("列表项" + i);
		}
		adapter.setList(list);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();
//		mExtraCalendarView.setCurrentMonth(2017, 7);
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
		if (mViewModel != null) {
			mViewModel.snakeBarText.removeOnPropertyChangedCallback(mSnakeBarCallback);
		}
	}
}
