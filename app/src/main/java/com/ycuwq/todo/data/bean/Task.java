package com.ycuwq.todo.data.bean;

/**
 * Created by 杨晨 on 2017/5/8.
 */
public class Task {
	private long id;

	private String title;

	private String description;

	private boolean complete;

	private long time;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean getComplete() {
		return this.complete;
	}
}
