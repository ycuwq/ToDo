package com.yangchen.extracalendarview.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

	private boolean mRunning = false;       //进行变换中

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
		//对滑动的处理。对Calendar部分使用scrollY()进行滑动，对RecyclerView部分使用offsetTopAndBottom进行滑动
		if (dy > 0 && (child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_MONTH || mRunning)) {
			//上滑的处理事项
			consumed[1] = dy;
			if (!mRunning && child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_MONTH) {
				//当变成周模式在切换成月模式时，会导致RecyclerView坐标异常，当上滑时重新layout一下可以恢复
//				Log.d(TAG, "onNestedPreScroll: requestLayout");
//				target.requestLayout();
				mRunning = true;
			}
			//点击View上方距离顶部的距离
			int clickViewTop = clickView.getTop();
			//clickView下方应该收缩的距离
			int surplusBottom = target.getTop() - (calendarView.getTop() + clickView.getHeight());
//			int surplusBottom = (calendarView.getHeight() - clickView.getHeight()) + target.getTop();
			Log.d(TAG, "onNestedPreScroll: surplusBottom" + surplusBottom);
			if (clickViewTop > calendarView.getScrollY()) {
				int offset = Math.min(clickViewTop - calendarView.getScrollY(), dy);
				calendarView.scrollBy(0, offset);
				ViewCompat.offsetTopAndBottom(target, -offset);
			} else if (surplusBottom > 0) {
//				Log.d(TAG, "onNestedPreScroll: target.getTop" + target.getTop());
//				Log.d(TAG, "onNestedPreScroll: target.getTranY" + target.getTranslationY());
//				Log.d(TAG, "onNestedPreScroll: target.getY" + target.getY());
				int offset = Math.min(surplusBottom, dy);
				ViewCompat.offsetTopAndBottom(target, -offset);
			} else if (clickViewTop <= calendarView.getScrollY() && surplusBottom<=0) {
				if (child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_MONTH) {
					Log.d(TAG, "onNestedPreScroll: change");
					child.changeCalendarType();
					calendarView.scrollTo(0, 0);
				}
				mRunning = false;
			}

		} else if (dy < 0 && mRunning) {
			int surplus = child.getBottom() - child.getClickView().getTop();
			if (surplus > target.getY()) {
				int offset = (int) Math.min(surplus - target.getY(), -dy);
				ViewCompat.offsetTopAndBottom(target, offset);
			} else if (calendarView.getScrollY() > 0) {
				int offset = Math.min(calendarView.getScrollY(), -dy);
				calendarView.scrollBy(0, -offset);
				ViewCompat.offsetTopAndBottom(target, offset);
			} else {
				mRunning = false;
				child.setCalendarType(ExtraCalendarView.CALENDAR_TYPE_MONTH);
			}
		} else if (dy < 0 && !mRunning && child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_WEEK) {
			//FIXME 修复第一次上滑到周模式，切换时出现dy = -419，导致调用此处。
			mRunning = true;
			Log.d(TAG, "onNestedPreScroll: change to Month");
			consumed[1] = dy;
			child.changeCalendarStyle();
			calendarView.scrollTo(0, child.getClickView().getTop());
		}
		Log.d(TAG, "onNestedPreScroll: " + dy);
	}

	@Override
	public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target) {
		super.onStopNestedScroll(coordinatorLayout, child, target);
//		Log.d(TAG, "onStopNestedScroll: ");

	}

	@Override
	public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, float velocityX, float velocityY) {
//		Log.d(TAG, "onNestedPreFling: " + velocityY);
		child.getClickDate();
		return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
	}
}
