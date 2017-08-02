package com.yangchen.extracalendarview;

import android.content.Context;

import com.yangchen.extracalendarview.base.Date;

/**
 * Created by 杨晨 on 2017/8/2.
 */
public class WeekItemView extends CalendarItemView {
	public WeekItemView(ExtraCalendarView extraCalendarView, Context context) {
		super(extraCalendarView, context);
		maxRow = 1;
	}

	@Override
	DayView createDayView(Context context, Date date, DayItemAttrs mDayItemAttrs) {
		return new DayView(context, date, mDayItemAttrs, false);
	}
}
