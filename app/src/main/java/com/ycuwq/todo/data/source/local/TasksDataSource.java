package com.ycuwq.todo.data.source.local;

import com.ycuwq.todo.data.bean.Task;

import java.util.List;

/**
 * 数据访问接口
 * Created by 杨晨 on 2017/5/9.
 */
public interface TasksDataSource {

	interface GetTasksCallback {
		void onTasksLoaded(List<Task> tasks);
	}


	void saveTask(Task task);

	void saveTasks(List<Task> tasks);

	void getTasks(GetTasksCallback callback);

	void updateTask(Task task);

	void deleteTask(long id);

	void deleteTasks(List<Task> list);

	void deleteAll();


}
