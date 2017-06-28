package com.yangchen.extracalendarview.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;

import com.yangchen.extracalendarview.ExtraCalendarView;

import static android.content.ContentValues.TAG;

/**
 * 控制日历响应滑动的效果
 * Created by yangchen on 2017/6/26.
 */
public class CalendarViewBehavior extends CoordinatorLayout.Behavior<ExtraCalendarView> {

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View directTargetChild, View target, int nestedScrollAxes) {
		Log.d(TAG, "onStartNestedScroll: ");
		return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
	}

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		Log.d(TAG, "onNestedScroll: ");
		super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
	}
}
