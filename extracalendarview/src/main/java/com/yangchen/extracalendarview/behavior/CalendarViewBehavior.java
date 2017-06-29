package com.yangchen.extracalendarview.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.yangchen.extracalendarview.ExtraCalendarView;

/**
 * 控制日历响应滑动的效果
 * Created by yangchen on 2017/6/26.
 */
public class CalendarViewBehavior extends CoordinatorLayout.Behavior<ExtraCalendarView> {

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View directTargetChild, View target, int nestedScrollAxes) {
		final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0
				&& coordinatorLayout.getHeight() - directTargetChild.getHeight() <= child.getHeight();
		return started;
	}

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		//如果在最上方还在下滑
		if (dyUnconsumed < 0) {
			child.offsetCalendarView(- dyUnconsumed);
			target.offsetTopAndBottom(- dyUnconsumed);
		}  else if (dyConsumed > 0) {
			child.offsetCalendarView(- dyConsumed);
			target.offsetTopAndBottom(- dyConsumed);
		}
	}

}
