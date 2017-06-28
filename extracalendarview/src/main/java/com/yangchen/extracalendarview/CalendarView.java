package com.yangchen.extracalendarview;

import android.content.Context;
import android.support.v4.view.ViewPager;

/**
 * 包装ViewPager
 * 主要用来计算MonthItemView的高度
 * Created by yangchen on 2017/6/22.
 */
public class CalendarView extends ViewPager {
	public CalendarView(Context context) {
		super(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int calendarHeight;
		if (getAdapter() != null) {
			CalendarItemView view = (CalendarItemView) getChildAt(0);
			if (view != null) {
				calendarHeight = view.getMeasuredHeight();
				setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calendarHeight, MeasureSpec.EXACTLY));
			}
		}
	}
}
