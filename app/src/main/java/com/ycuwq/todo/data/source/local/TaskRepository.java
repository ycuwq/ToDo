package com.ycuwq.todo.data.source.local;

import android.arch.lifecycle.LiveData;

import com.ycuwq.todo.AppExecutors;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.source.local.dao.TaskDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 对Task进行数据库交互
 * Created by ycuwq on 2018/2/5.
 */
@Singleton
public class TaskRepository {
    private final TaskDao mTaskDao;
    private final AppExecutors mAppExecutors;

    @Inject
    public TaskRepository(TaskDao taskDao, AppExecutors appExecutors) {
        mTaskDao = taskDao;
        mAppExecutors = appExecutors;
    }

    public void saveTask(Task task) {
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (task.getId() > 0) {
                    mTaskDao.updateTask(task);
                } else {
                    mTaskDao.insertTask(task);
                }
            }
        });
    }

    public LiveData<List<Task>> loadAllTask() {
        return mTaskDao.loadAllTask();
    }
    public LiveData<List<Task>> loadTasks(String date) {
        return mTaskDao.loadAllTask(date);
    }

}
