package com.ycuwq.todo.view.tasks;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangchen.extracalendarview.ExtraCalendarView;
import com.yangchen.extracalendarview.base.Date;
import com.yangchen.extracalendarview.listener.OnDayClickListener;
import com.ycuwq.common.recycler.ExRecyclerAdapter;
import com.ycuwq.common.recycler.ExRecyclerViewHolder;
import com.ycuwq.todo.R;
import com.ycuwq.todo.base.ViewModelFragment;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.databinding.FragTasksBinding;
import com.ycuwq.todo.di.Injectable;
import com.ycuwq.todo.view.edittask.EditTaskActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * 显示任务总览的Activity
 * Created by 杨晨 on 2017/5/10.
 */
public class TasksFragment extends ViewModelFragment implements Injectable {

	private final String TAG = getClass().getSimpleName();

	private FragTasksBinding mBinding;

	private	TasksViewModel mViewModel;

	@Inject
	ViewModelProvider.Factory mViewModelFactory;

	private ExtraCalendarView mExtraCalendarView;
	private Observable.OnPropertyChangedCallback mSnackbarCallback;

	@Inject
	public TasksFragment() {}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		mBinding = FragTasksBinding.inflate(inflater, container, false);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TasksViewModel.class);
        registerSnackbarText(mViewModel);
		return mBinding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();

	}

	private void initView() {
        mExtraCalendarView = mBinding.extraCalendarView;
        mExtraCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onClick(View v, Date date) {
                mViewModel.getDate().setValue(date.toString());
            }
        });
		mBinding.fabAddTask.setOnClickListener(v -> jumpAddTaskActivity());
		RecyclerView recyclerView = mBinding.recyclerView;
		ExRecyclerAdapter<Task> adapter = new ExRecyclerAdapter<Task>(getContext(), R.layout.item_task) {
			@Override
			public void bindData(ExRecyclerViewHolder holder, Task task, int position) {
				TextView textView = holder.getView(R.id.tv_item_task_name);
				if (task.getType() == Task.TYPE_BIRTHDAY) {
                    textView.setText(String.format("%s%s", task.getName(), getString(R.string.birthday_suffix)));
                } else {
				    textView.setText(task.getName());
                }
				holder.getRootView().setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});
			}
		};
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
		recyclerView.setAdapter(adapter);
        mViewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                adapter.setList(tasks);
            }
        });
	}

	@Override
	public void onResume() {
		super.onResume();
	}


	public void jumpAddTaskActivity() {
		startActivity(EditTaskActivity.getIntent(getContext(), null));
	}


}
