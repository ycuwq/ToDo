package com.yangchen.extracalendarview;

import android.content.Context;

import com.yangchen.extracalendarview.base.Date;

/**
 * Created by 杨晨 on 2017/8/2.
 */
public class MonthItemView extends CalendarItemView{
	public MonthItemView(ExtraCalendarView extraCalendarView, Context context) {
		super(extraCalendarView, context);
		maxRow = 6;
	}

	@Override
	DayView createDayView(Context context, Date date, DayItemAttrs mDayItemAttrs) {
		return new DayView(context, date, mDayItemAttrs, true);
	}

}
