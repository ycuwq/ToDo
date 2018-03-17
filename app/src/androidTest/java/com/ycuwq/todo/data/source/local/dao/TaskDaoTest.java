package com.ycuwq.todo.data.source.local.dao;

import android.support.test.runner.AndroidJUnit4;

import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.util.TaskCreateUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * 测试TaskDao
 * Created by yangchen on 2017/12/8.
 */
@RunWith(AndroidJUnit4.class)
public class TaskDaoTest extends DbTest{

	@Test
	public void insertAndRead() throws Exception {
		String date = "2012-01-01";
		Task task = TaskCreateUtil.createTask("Banana", date);
		db.taskDao().insertTask(task);

		List<Task> tasks = db.taskDao().loadAllTaskList();
		Task task1 = tasks.get(tasks.size() - 1);
		assertThat(task1.getName(), is("Banana"));
		assertThat(task1.getStartDate(), is(date));
	}

	@Test
	public void updateTask() throws Exception {
	}

	@Test
	public void deleteTask() throws Exception {
	}

}