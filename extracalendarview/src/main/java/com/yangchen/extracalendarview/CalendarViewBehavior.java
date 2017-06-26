package com.yangchen.extracalendarview;

import android.support.design.widget.CoordinatorLayout;
import android.view.MotionEvent;

/**
 * 控制日历响应滑动的效果
 * Created by yangchen on 2017/6/26.
 */
public class CalendarViewBehavior extends CoordinatorLayout.Behavior<ExtraCalendarView> {
	@Override
	public boolean onInterceptTouchEvent(CoordinatorLayout parent, ExtraCalendarView child, MotionEvent ev) {
		return super.onInterceptTouchEvent(parent, child, ev);
	}
}
