package com.ycuwq.todo.data.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * 在room框架中使用的日期转换器
 * Created by 杨晨 on 2017/12/7.
 */
public class DateConverter {
	@TypeConverter
	public static Date fromTimestamp(Long value) {
		return value == null ? null : new Date(value);
	}

	@TypeConverter
	public static Long dateToTimestamp(Date date) {
		return date == null ? null : date.getTime();
	}
}
