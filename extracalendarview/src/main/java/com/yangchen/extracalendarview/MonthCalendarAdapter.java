package com.yangchen.extracalendarview;

import android.content.Context;

import com.yangchen.extracalendarview.base.Date;
import com.yangchen.extracalendarview.util.CalendarUtil;

import java.util.List;

/**
 * Created by 杨晨 on 2017/8/2.
 */
public class MonthCalendarAdapter extends CalendarAdapter {
	MonthCalendarAdapter(ExtraCalendarView extraCalendarView, int count, int startYear, int startMonth, DayItemAttrs dayItemAttrs) {
		super(extraCalendarView, count, startYear, startMonth, dayItemAttrs);
	}

	@Override
	public CalendarItemView instantiateCalendarView(ExtraCalendarView extraCalendarView, Context context) {
		return new MonthItemView(extraCalendarView, context);
	}

	@Override
	public List<Date> getCalendarDates(int startYear, int startMonth, int position) {
		int date[] = CalendarUtil.positionToDate(position, startYear, startMonth);
		return CalendarUtil.getMonthDates(date[0], date[1]);
	}


}
