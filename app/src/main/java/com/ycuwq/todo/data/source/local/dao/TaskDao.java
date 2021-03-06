package com.ycuwq.todo.data.source.local.dao;

		import android.arch.lifecycle.LiveData;
		import android.arch.persistence.room.Dao;
		import android.arch.persistence.room.Delete;
		import android.arch.persistence.room.Insert;
		import android.arch.persistence.room.Query;
		import android.arch.persistence.room.Update;

		import com.ycuwq.todo.data.bean.Task;

		import java.util.List;

/**
 * Task的查询方法
 * Created by 杨晨 on 2017/12/7.
 */
@Dao
public interface TaskDao {

	@Insert
	void insertTask(Task task);

	@Update
	void updateTask(Task task);

	@Delete
	void deleteTask(Task task);

	@Query("SELECT * FROM task ORDER BY startTime")
	LiveData<List<Task>> loadAllTasks();

	@Query("SELECT * FROM task")
	List<Task> loadAllTaskList();

	@Query("SELECT * FROM task WHERE startDate = :startDate")
	LiveData<List<Task>> loadAllTasks(String startDate);

	@Query("SELECT * FROM task WHERE type = :type ORDER BY startTime")
    List<Task> loadAllTaskList(int type);
}
