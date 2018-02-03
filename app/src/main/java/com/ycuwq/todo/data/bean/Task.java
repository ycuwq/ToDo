package com.ycuwq.todo.data.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.ycuwq.todo.BR;
import com.ycuwq.todo.data.converters.DateConverter;

import java.util.Date;

/**
 * 任务实体类
 * Created by 杨晨 on 2017/5/8.
 */
@SuppressWarnings("unused")
@Entity(indices = {@Index(value = "startDate")})
@TypeConverters(DateConverter.class)
public class Task implements Observable {

	public static final int TYPE_SCHEDULE = 1;

	public static final int TYPE_BIRTHDAY = 2;

	public static final int TYPE_ANNIVERSARY = 3;

	/**
	 * 不重复
	 */
	public static final int REPEAT_NULL = 0;

	public static final int REPEAT_DAY = 1;

	public static final int REPEAT_WORK_DAY = 2;

	public static final int REPEAT_WEEK = 3;

	public static final int REPEAT_MONTH = 4;

	public static final int REPEAT_YEAR = 5;

	@PrimaryKey(autoGenerate = true)
	private int id;

	/**
	 * 任务类型：SCHEDULE，BIRTHDAY，ANNIVERSARY
	 */
	private int type;

	private String name;

	/**
	 * 是否已经完成
	 */
	private boolean isCompleted;

	/**
	 * 是否是全天事件
	 */
	private boolean isAllDay;


	private int year;

	private int month;

	private int day;

	/**
	 * 开始日期
	 */
	private String startDate;

	private Date startTime;

	private Date reminderTime;

	/**
	 * 重复模式，每天，每周，等模式
	 */
	private int repeat;

    /**
     * 地点
     */
	private String address;

    /**
     * 备注
     */
	private String remark;

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	public Task() {
	}


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

	@Bindable
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		notifyChange(BR.name);
	}

	@Bindable
	public boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(boolean completed) {
		isCompleted = completed;
		notifyChange(BR.isCompleted);
	}

	public boolean getIsAllDay() {
		return isAllDay;
	}

	public void setAllDay(boolean allDay) {
		isAllDay = allDay;
	}

	@Bindable
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		notifyChange(BR.startTime);
	}

	@Bindable
	public Date getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(Date reminderTime) {
		this.reminderTime = reminderTime;
		notifyChange(BR.reminderTime);
	}

	@Bindable
	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
		notifyChange(BR.repeat);
	}

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Bindable
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
		notifyChange(BR.startDate);
	}

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyChange(BR.address);
    }

    @Bindable
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
        notifyChange(BR.remark);
    }

    private void notifyChange(int propertyId) {
		if (propertyChangeRegistry == null) {
			propertyChangeRegistry = new PropertyChangeRegistry();
		}
		propertyChangeRegistry.notifyChange(this, propertyId);
	}

	@Override
	public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
		if (propertyChangeRegistry == null) {
			propertyChangeRegistry = new PropertyChangeRegistry();
		}
		propertyChangeRegistry.add(callback);

	}

	@Override
	public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
		if (propertyChangeRegistry != null) {
			propertyChangeRegistry.remove(callback);
		}
	}
}
