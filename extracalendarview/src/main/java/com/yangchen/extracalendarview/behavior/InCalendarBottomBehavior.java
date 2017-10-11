package com.yangchen.extracalendarview.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yangchen.extracalendarview.ExtraCalendarView;

/**
 * 让View 显示在{@link ExtraCalendarView} 的下面。
 * Created by yangchen on 2017/6/28.
 */
public class InCalendarBottomBehavior extends CoordinatorLayout.Behavior<View> {

	private final String TAG = getClass().getSimpleName();
	public InCalendarBottomBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
		parent.onLayoutChild(child, layoutDirection);
		ExtraCalendarView extraCalendarView = (ExtraCalendarView) parent.getChildAt(0);
		int calendarHeight = extraCalendarView.getCalendarHeight();
		//在切换周月的时候extraCalendarView没有绘制结束，导致Height为0。这里等待
		if (calendarHeight == 0 || calendarHeight == extraCalendarView.getCalendarLayout().getTop()) {
			if (extraCalendarView.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_WEEK) {
				ViewCompat.offsetTopAndBottom(child, ExtraCalendarView.weekCalendarHeight);
			}
//			else {
//				extraCalendarView.post(() -> {
//					ViewCompat.offsetTopAndBottom(child, extraCalendarView.getCalendarHeight());
//				});
//			}
		} else {

			ViewCompat.offsetTopAndBottom(child, extraCalendarView.getCalendarHeight());
		}
		return true;
	}

}
