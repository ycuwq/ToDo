package com.ycuwq.todo.data.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * 任务实体类
 * Created by 杨晨 on 2017/5/8.
 */
@Entity
public class Task {


	public static final int TYPE_SCHEDULE = 1;

	public static final int TYPE_BIRTHDAY = 2;

	public static final int TYPE_ANNIVERSARY = 3;

	/**
	 * 不重复
	 */
	public static final int REPEAT_NULL = 1;

	public static final int REPEAT_DAY = 2;

	public static final int REPEAT_WEEK = 3;

	public static final int REPEAT_MONTH = 4;

	public static final int REPEAT_YEAR = 5;

	@PrimaryKey
	private int id;

	/**
	 * 任务类型：SCHEDULE，BIRTHDAY，ANNIVERSARY
	 */
	private int type;

	private String name;

	/**
	 * 是否已经完成
	 */
	private boolean isComplete;

	/**
	 * 是否是全天事件
	 */
	private boolean isAllDay;

	private Date startTime;

	private Date reminderTime;

	/**
	 * 重复模式，每天，每周，等模式
	 */
	private int repeat;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean complete) {
		isComplete = complete;
	}

	public boolean isAllDay() {
		return isAllDay;
	}

	public void setAllDay(boolean allDay) {
		isAllDay = allDay;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(Date reminderTime) {
		this.reminderTime = reminderTime;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
}
