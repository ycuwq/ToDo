package com.ycuwq.todo.view.tasks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.text.TextUtils;
import android.util.Log;

import com.ycuwq.calendarview.CalendarView;
import com.ycuwq.calendarview.Date;
import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Task的ViewModel
 * Created by 杨晨 on 2017/5/9.
 */
public class TasksViewModel extends BaseViewModel {
	private final String TAG = getClass().getSimpleName();
	private final TaskRepository mTaskRepository;
	private final LiveData<List<Task>> mTasks;
	private final MutableLiveData<String> mDate;
	@Inject
	public TasksViewModel(TaskRepository taskRepository) {
	    mTaskRepository = taskRepository;
        mDate = new MutableLiveData<>();
        mTasks = Transformations.switchMap(mDate, input -> {
           if (TextUtils.isEmpty(input)) {
               return mTaskRepository.loadAllTasks();
           }
           return mTaskRepository.loadTasks(input);
        });
    }

    public LiveData<List<Task>> getTasks() {
        return mTasks;
    }

    public void addSchemeToCalendarView(CalendarView calendarView) {
        final int[] day = {-1};
        Observable.create(new ObservableOnSubscribe<List<Task>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Task>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(mTaskRepository.loadAllTaskList());
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Function<List<Task>, List<Date>>() {
                    @Override
                    public List<Date> apply(List<Task> tasks) throws Exception {
                        Log.d(TAG, "apply: " + tasks.size());
                        List<Date> dates = new ArrayList<>();
                        int day = -1;
                        for (Task task : tasks) {
                            if (task.getDay() != day) {
                                day = task.getDay();
                                dates.add(new Date(task.getYear(), task.getMonth(), task.getDay()));
                            }
                        }
                        return dates;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Date>>() {
                    @Override
                    public void accept(List<Date> dates) throws Exception {
                        calendarView.setSchemes(dates);
                    }
                });
    }

    public MutableLiveData<String> getDate() {
        return mDate;
    }

    public void onTaskClicked() {


	}
}
