package com.yangchen.extracalendarview.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.yangchen.extracalendarview.DayView;
import com.yangchen.extracalendarview.ExtraCalendarView;

/**
 * 控制日历响应滑动的效果
 * Created by yangchen on 2017/6/26.
 */
public class CalendarViewBehavior extends CoordinatorLayout.Behavior<ExtraCalendarView> {
	private final String TAG = getClass().getSimpleName();
	private int clickViewTop = Integer.MIN_VALUE;
	private float ox, oy;

	private boolean flag = false;

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View directTargetChild, View target, int nestedScrollAxes) {
//		Log.d(TAG, "onStartNestedScroll: ");
		final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;

		return started;
	}

	@Override
	public boolean onInterceptTouchEvent(CoordinatorLayout parent, ExtraCalendarView child, MotionEvent ev) {
//		Log.d(TAG, "onInterceptTouchEvent: ");
		return super.onInterceptTouchEvent(parent, child, ev);
	}

	@Override
	public boolean onTouchEvent(CoordinatorLayout parent, ExtraCalendarView child, MotionEvent ev) {
		return super.onTouchEvent(parent, child, ev);
//		return true;
	}

	@Override
	public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, int dx, int dy, int[] consumed) {
		FrameLayout calendarView = child.getCalendarLayout();
		DayView clickView = child.getClickView();
		if (clickView == null) {
			return;
		}
		if (target instanceof RecyclerView) {
			RecyclerView recyclerView = (RecyclerView)target;
			//RecyclerView已经无法上滑时折叠日历，否则返回。
			if (recyclerView.canScrollVertically(-1)) {
				return;
			}
		}
		int clickViewTop = clickView.getTop();
		int clickViewHeight = clickView.getHeight();
		int weekInfoBottom = child.getWeekInfoView().getBottom();

		if (dy > 0 && child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_MONTH) {
			consumed[1] = dy;
			int surplusTop = clickViewTop - (weekInfoBottom - calendarView.getTop());
			int surplusBottom = (calendarView.getHeight() - clickView.getHeight()) + target.getTop();
			if (surplusTop > calendarView.getScrollY()) {
				int offset = Math.min(surplusTop, dy);
				calendarView.scrollBy(0, offset);
//				ViewCompat.offsetTopAndBottom(calendarView, -offset);
				ViewCompat.offsetTopAndBottom(target, -offset);
			} else if (surplusBottom > 0) {
				int offset = Math.min(surplusBottom, dy);
//				target.scrollBy(0, offset);
				ViewCompat.offsetTopAndBottom(target, -offset);
			} else if (surplusTop <= calendarView.getScrollY() && surplusBottom<=0) {
				child.changeCalendarType();
				calendarView.scrollTo(0, 0);
			}
//			} else if ((-target.getTop()) >= (calendarView.getHeight() - clickView.getHeight())) {
//				child.changeCalendarType();
//			}
		} else if (dy < 0 && flag) {
			int range = (int) (child.getBottom() - target.getY());
			if (range > 0) {
				if (range > -dy) {
					ViewCompat.offsetTopAndBottom(target, -dy);
				} else {
					ViewCompat.offsetTopAndBottom(target, range);
					child.changeCalendarType();
//					child.setCalendarType(ExtraCalendarView.CALENDAR_TYPE_MONTH);
				}
			}
		} else if (dy < 0 && !flag) {
			consumed[1] = dy;
			int surplusTop = clickViewTop - (weekInfoBottom - calendarView.getTop());
			child.changeCalendarStyle();
			calendarView.scrollTo(0, surplusTop);
			flag = true;
//			ViewCompat.offsetTopAndBottom(calendarView, -dy);
//			child.changeCalendarType();
//			int surplusTop = clickViewTop - (weekInfoBottom - calendarView.getTop());
//			int surplusBottom = (calendarView.getHeight() - clickView.getHeight()) + target.getTop();
//			ViewCompat.offsetTopAndBottom(calendarView, -surplusTop);
//			ViewCompat.offsetTopAndBottom(target, -surplusBottom);
//			consumed[1] = dy;
		}


	}


	@Override
	public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, float velocityX, float velocityY) {
//		Log.d(TAG, "onNestedPreFling: " + velocityY);
		child.getClickDate();
		return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
	}
}
