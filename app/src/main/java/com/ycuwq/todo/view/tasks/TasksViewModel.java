package com.ycuwq.todo.view.tasks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.text.TextUtils;

import com.ycuwq.calendarview.Date;
import com.ycuwq.calendarview.PagerInfo;
import com.ycuwq.todo.base.BaseViewModel;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.TaskRepository;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Task的ViewModel
 * Created by 杨晨 on 2017/5/9.
 */
public class TasksViewModel extends BaseViewModel {

	private final TaskRepository mTaskRepository;

	private final LiveData<List<Task>> mTasks;

    /**
     * 当前日期
     */
	private final MutableLiveData<String> mCurrentDate;

//    private final LiveData<List<Date>> mCurrentScheme;

    private final HashMap<String, List<Date>> mSchemeMap = new HashMap<>();

    private final LiveData<PagerInfo> mCurrentPager;

	@Inject
	public TasksViewModel(TaskRepository taskRepository) {
	    mTaskRepository = taskRepository;
        mCurrentDate = new MutableLiveData<>();
        mCurrentPager = new MutableLiveData<>();
        mTasks = Transformations.switchMap(mCurrentDate, input -> {
           if (TextUtils.isEmpty(input)) {
               return mTaskRepository.loadAllTasks();
           }
           return mTaskRepository.loadTasks(input);
        });
    }

    public LiveData<List<Task>> getTasks() {
        return mTasks;
    }

    List<Date> getScheme(PagerInfo pagerInfo) {
        List<Date> schemes = new ArrayList<>();
        Date tempDate = new Date();
        if (pagerInfo.getType() == PagerInfo.TYPE_MONTH) {
            LocalDate currentDate = new LocalDate(pagerInfo.getYear(), pagerInfo.getMonth(), 1);

            LocalDate firstWeekday = currentDate.withDayOfWeek(1);
            //加入该月显示的第一个星期而且是非当月信息的Scheme
            int week = 1;
            while (firstWeekday.getMonthOfYear() != pagerInfo.getMonth()) {
                tempDate.setYear(firstWeekday.getYear());
                tempDate.setMonth(firstWeekday.getMonthOfYear());
                tempDate.setDay(firstWeekday.getDayOfMonth());
                addSchemeToList(schemes, tempDate, Date.TYPE_LAST_MONTH);
                firstWeekday = firstWeekday.withDayOfWeek(++week);
            }
            List<Date> currentMonthScheme = mSchemeMap.get(pagerInfo.getYear() + "-" + pagerInfo.getMonth());
            if (currentMonthScheme != null) {
                schemes.addAll(currentMonthScheme);
            }
            LocalDate lastWeekday = currentDate.plusMonths(1);
            if (lastWeekday.getDayOfWeek() != 1) {
                //加入该月显示的最后一个星期而且非当月信息的Scheme
                for (int i = lastWeekday.getDayOfWeek(); i <=7; i++) {
                    LocalDate weekDay = lastWeekday.withDayOfWeek(i);
                    tempDate.setYear(weekDay.getYear());
                    tempDate.setMonth(weekDay.getMonthOfYear());
                    tempDate.setDay(weekDay.getDayOfMonth());
                    addSchemeToList(schemes, tempDate, Date.TYPE_NEXT_MONTH);
                }
            }
        } else {
            LocalDate localDate = new LocalDate(pagerInfo.getYear(), pagerInfo.getMonth(), pagerInfo.getMondayDay());
            for (int i = 1; i <= 7; i++) {
                LocalDate weekDay = localDate.withDayOfWeek(i);
                tempDate.setYear(weekDay.getYear());
                tempDate.setMonth(weekDay.getMonthOfYear());
                tempDate.setDay(weekDay.getDayOfMonth());
                tempDate.setType(Date.TYPE_THIS_MONTH);
                addSchemeToList(schemes, tempDate, Date.TYPE_THIS_MONTH);
            }
        }

        return schemes;
    }

    /**
     * 将Scheme加入到list中
     * @param schemes   要加入的list
     * @param target    目标日期
     * @param dateType  设置的日期类型。这里要注意：这里的从mSchemeMap获取的scheme是从Task中转换的，
     *                  是没有设置Type的值的，所以默认是TYPE_THIS_MONTH。
     *                  Date类的equals比较是年月日和日期类型都相等才可以，
     *                  但是返回给CalendarView使用的Date是有Type的，所以这边传递给CalendarView要设置类型进入，
     *                  否则在当前月份显示的其他月份的Scheme会无效。
     */
    private void addSchemeToList(List<Date> schemes, Date target, int dateType) {
        int index;
        List<Date> monthScheme = mSchemeMap.get(target.getYear() + "-" + target.getMonth());
        if (monthScheme != null && (index = monthScheme.indexOf(target)) > -1) {
            Date date = monthScheme.get(index);
            Date clone = new Date(date.getYear(), date.getMonth(), date.getDay());
            clone.setType(dateType);
            schemes.add(clone);
        }
    }

    void updateScheme() {
        Observable.create((ObservableOnSubscribe<List<Task>>) e -> {
            e.onNext(mTaskRepository.loadAllTaskList());
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(tasks -> {
                    int day = -1;
                    for (Task task : tasks) {
                        //scheme只要一天有一个Task即可生效，多余的无用
                        if (task.getDay() != day) {
                            day = task.getDay();
                            List<Date> dates = mSchemeMap.get(task.getYear() + "-" + task.getMonth());
                            if (dates == null) {
                                dates = new ArrayList<>();
                                mSchemeMap.put(task.getYear() + "-" + task.getMonth(), dates);
                            }
                            dates.add(new Date(task.getYear(), task.getMonth(), task.getDay()));
                        }

                    }
                    return mSchemeMap;
                })
                .subscribe(new Consumer<HashMap<String, List<Date>>>() {
                    @Override
                    public void accept(HashMap<String, List<Date>> stringListHashMap) throws Exception {

                    }
                });
    }

    MutableLiveData<String> getCurrentDate() {
        return mCurrentDate;
    }

}
