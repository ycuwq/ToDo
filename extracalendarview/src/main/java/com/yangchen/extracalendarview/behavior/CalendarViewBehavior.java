package com.yangchen.extracalendarview.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import com.yangchen.extracalendarview.ExtraCalendarView;

/**
 * 控制日历响应滑动的效果
 * Created by yangchen on 2017/6/26.
 */
public class CalendarViewBehavior extends CoordinatorLayout.Behavior<ExtraCalendarView> {
	private final String TAG = getClass().getSimpleName();
	private int clickViewTop = Integer.MIN_VALUE;
	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View directTargetChild, View target, int nestedScrollAxes) {
		final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0
				&& coordinatorLayout.getHeight() - directTargetChild.getHeight() <= child.getHeight();
		return started;
	}

	@Override
	public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target) {
		super.onStopNestedScroll(coordinatorLayout, child, target);
		clickViewTop = child.getClickView().getTop();
	}

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		//如果在最上方还在下滑

		if (dyUnconsumed < 0) {
			if (clickViewTop >= child.getClickView().getTop()) {
				return;
			}
			clickViewTop -= dyUnconsumed;
			child.getCalendarView().setTranslationY(child.getCalendarView().getTranslationY() - dyUnconsumed);
			target.setY(target.getY() - dyUnconsumed);

		}  else if (dyConsumed > 0) {
			clickViewTop -= dyConsumed;

			if (clickViewTop > 0) {
				Log.d(TAG, "onNestedScroll: " + child.getPaddingTop());
				child.getCalendarView().setTranslationY(child.getCalendarView().getTranslationY() - dyConsumed);
				target.offsetTopAndBottom(-dyConsumed);
			}
		}
	}

}
