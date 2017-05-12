package com.ycuwq.todo.data.source.local;

import android.content.Context;

import com.ycuwq.todo.common.util.RxJava2Helper;
import com.ycuwq.todo.data.bean.DaoMaster;
import com.ycuwq.todo.data.bean.DaoSession;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.data.bean.TaskDao;
import com.ycuwq.todo.data.source.TaskDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * 本地数据库
 * 利用Dagger2 实现单例模式
 * Created by 杨晨 on 2017/5/9.
 */
@Singleton
public class TaskLocalDataSource implements TaskDataSource{

	private TaskDao mTaskDao;

	@Inject
	public TaskLocalDataSource(Context context) {
		//加载数据库
		DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "tasks-db");
		DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
		DaoSession daoSession = daoMaster.newSession();

		mTaskDao = daoSession.getTaskDao();
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
			observableEmitter.onComplete();})
			.compose(RxJava2Helper.io2Main())
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
