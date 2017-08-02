package com.yangchen.extracalendarview;

import android.content.Context;

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


}
