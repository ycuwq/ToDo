package com.ycuwq.todo.data.source.local;

import com.ycuwq.todo.app.App;
import com.ycuwq.todo.common.util.RxJava2Helper;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.bean.TaskDao;
import com.ycuwq.todo.data.source.TaskDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by 杨晨 on 2017/5/9.
 */
public class TaskLocalDataSource implements TaskDataSource{

	private TaskDao mTaskDao;

	public TaskLocalDataSource() {
		mTaskDao = App.getmDaoSession().getTaskDao();
	}


	@Override
	public void saveTask(Task task) {
		mTaskDao.insert(task);
	}

	@Override
	public void saveTasks(List<Task> tasks) {
		mTaskDao.insertInTx(tasks);
	}

	@Override
	public void getTasks(GetTasksCallback callback) {
		Observable.create((ObservableOnSubscribe<List<Task>>) observableEmitter -> {
			observableEmitter.onNext(mTaskDao.queryBuilder().list());
			observableEmitter.onComplete();
		}).compose(RxJava2Helper.io2Main())
				.subscribe(callback::onTasksLoaded);

	}

	@Override
	public void updateTask(Task task) {
		mTaskDao.update(task);
	}

	@Override
	public void deleteTask(long id) {
		mTaskDao.deleteByKey(id);
	}

	@Override
	public void deleteTasks(List<Task> list) {
		mTaskDao.deleteInTx(list);
	}

	@Override
	public void deleteAll() {
		mTaskDao.deleteAll();
	}
}
