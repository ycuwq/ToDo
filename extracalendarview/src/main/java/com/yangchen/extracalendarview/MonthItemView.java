package com.yangchen.extracalendarview;

import android.content.Context;

import com.yangchen.extracalendarview.base.Date;
import com.yangchen.extracalendarview.listener.OnDayViewClickListener;

/**
 * Created by 杨晨 on 2017/8/2.
 */
public class MonthItemView extends BaseCalendarItemView {
	public MonthItemView(OnDayViewClickListener onDayViewClickListener, Context context) {
		super(onDayViewClickListener, context);
		maxRow = 6;
	}

	@Override
	DayView createDayView(Context context, Date date, DayItemAttrs mDayItemAttrs) {
		return new DayView(context, date, mDayItemAttrs, true);
	}

}
