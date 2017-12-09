package com.ycuwq.todo.util;

import com.ycuwq.common.util.DateUtil;
import com.ycuwq.todo.data.bean.Task;

import java.util.Date;

/**
 * 生成Task实体类，方便测试。
 * Created by yangchen on 2017/12/8.
 */
public class TaskCreateUtil {
	public static Task createTask(String name) {
		Date date = new Date(System.currentTimeMillis());
		Task task = new Task(Task.TYPE_ANNIVERSARY, name, false, false, DateUtil.getNowDateShort(), date,
				date, Task.REPEAT_NULL);
		return task;
	}

	public static Task createTask(String name, String startDate) {
		Date date = new Date(System.currentTimeMillis());
		Task task = new Task(Task.TYPE_ANNIVERSARY, name, false, false, startDate, date,
				date, Task.REPEAT_NULL);
		return task;
	}

	public static Task createTask(int type, String name, boolean isComplete, boolean isAllDay,
	                              String startDate, Date startTime, Date reminderTime, int repeat) {
		return new Task(type, name, isComplete, isAllDay, startDate, startTime, reminderTime, repeat);
	}
}
