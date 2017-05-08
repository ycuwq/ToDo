package com.ycuwq.todo.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 杨晨 on 2017/5/8.
 */
@Entity
public class Task {
	@Id
	private long id;

	private String title;

	private String description;

	private boolean complete;

	private long time;

	@Generated(hash = 2024943498)
	public Task(long id, String title, String description, boolean complete,
			long time) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.complete = complete;
		this.time = time;
	}

	@Generated(hash = 733837707)
	public Task() {
	}

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
