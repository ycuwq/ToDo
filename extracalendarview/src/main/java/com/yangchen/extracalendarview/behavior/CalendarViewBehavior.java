package com.yangchen.extracalendarview.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yangchen.extracalendarview.ExtraCalendarView;

/**
 * 控制日历响应滑动的效果
 * Created by yangchen on 2017/6/26.
 */
public class CalendarViewBehavior extends CoordinatorLayout.Behavior<ExtraCalendarView> {
	private final String TAG = getClass().getSimpleName();
	private int clickViewTop = Integer.MIN_VALUE;
	private int top;
	private float ox, oy;

	@Override
	public boolean onLayoutChild(CoordinatorLayout parent, ExtraCalendarView child, int layoutDirection) {
		parent.onLayoutChild(child, layoutDirection);
		child.offsetTopAndBottom(top);
		return true;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, ExtraCalendarView child, View dependency) {
		top = child.getTop();
		return true;
	}
	
	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View directTargetChild, View target, int nestedScrollAxes) {
		final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
		return started;
	}

	@Override
	public boolean onInterceptTouchEvent(CoordinatorLayout parent, ExtraCalendarView child, MotionEvent ev) {
//		Log.d(TAG, "onInterceptTouchEvent: " + child.getCalendarView().getTop());
		return super.onInterceptTouchEvent(parent, child, ev);
//		return true;
	}

	@Override
	public boolean onTouchEvent(CoordinatorLayout parent, ExtraCalendarView child, MotionEvent ev) {
		return super.onTouchEvent(parent, child, ev);
//		return true;
	}

	@Override
	public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, int dx, int dy, int[] consumed) {
		super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
	}

	@Override
	public boolean onNestedFling(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, float velocityX, float velocityY, boolean consumed) {
		return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
	}

	@Override
	public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, float velocityX, float velocityY) {
		return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
	}

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		int clickViewTop = child.getClickView().getTop();
		int clickViewHeight = child.getClickView().getHeight();
		float calendarViewTop = child.getCalendarView().getTop();
		float calendarViewY = child.getCalendarView().getY();
		float targetViewY = target.getTop();
		//如果在最上方还在下滑
		if (dyUnconsumed < 0) {
//			ViewCompat.offsetTopAndBottom(child.getCalendarView(), -dyUnconsumed);
			child.getCalendarView().setTranslationY(child.getCalendarView().getTranslationY() - dyUnconsumed);
//			target.setY(target.getY() - dyUnconsumed);

		}  else if (dyConsumed > 0) {
			//这里利用Translation移动View，而不是ViewCompat.offsetTopAndBottom，因为移动Translation View的Top不变只有Y和TranslationY改变，
			//可以方便的利用Top - Y计算出移动的距离
			float dy = clickViewTop - (calendarViewTop - calendarViewY);
			//判断点击的View是否还在页面上。
			if (dy > 0) {
				//这里判断选中的日期View的距离是否大于滑动的距离，防止滑动速度过快而遮挡View
				if (dy - dyConsumed > 0) {
					dy = dyConsumed;
				}
				setMoveY(child.getCalendarView(), -dy);
			} else if (child.getBottom() > (calendarViewTop + clickViewHeight)){
				child.setBottom(child.getBottom() - dyConsumed);
			}
		}
		Log.d(TAG, "getBottom: " + child.getBottom());
		Log.d(TAG, "clickViewBottom: " + calendarViewTop);
	}

	private void setMoveY(View view, float dy) {
		view.setTranslationY(view.getTranslationY() + dy);
	}
}
