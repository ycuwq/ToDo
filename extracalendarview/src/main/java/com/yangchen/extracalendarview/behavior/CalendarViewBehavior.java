package com.yangchen.extracalendarview.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yangchen.extracalendarview.CalendarView;
import com.yangchen.extracalendarview.DayView;
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
		Log.d(TAG, "onStartNestedScroll: ");
		final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;

		return started;
	}

	@Override
	public boolean onInterceptTouchEvent(CoordinatorLayout parent, ExtraCalendarView child, MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent: ");
		return super.onInterceptTouchEvent(parent, child, ev);
	}

	@Override
	public boolean onTouchEvent(CoordinatorLayout parent, ExtraCalendarView child, MotionEvent ev) {
		return super.onTouchEvent(parent, child, ev);
//		return true;
	}

	@Override
	public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, int dx, int dy, int[] consumed) {
		CalendarView calendarView = child.getCalendarView();
		DayView clickView = child.getClickView();
		if (clickView == null) {
			return;
		}

		int clickViewTop = clickView.getTop();
		int clickViewHeight = clickView.getHeight();
		int weekInfoBottom = child.getWeekInfoView().getBottom();
		if (dy > 0) {
			if (clickViewTop > weekInfoBottom - calendarView.getTop()) {
				ViewCompat.offsetTopAndBottom(calendarView, -dy);
				ViewCompat.offsetTopAndBottom(target, -dy);
			}
		} else {
			ViewCompat.offsetTopAndBottom(calendarView, -dy);
			ViewCompat.offsetTopAndBottom(target, -dy);
		}
		Log.d(TAG, "onNestedPreScroll: " + child.getWeekInfoView().getBottom());
		Log.d(TAG, "clickViewTop: " + clickViewTop);
		Log.d(TAG, "calendarViewTop: " + calendarView.getTop());
		Log.d(TAG, "target: " + target.getTop());
	}

	//	@Override
//	public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, int dx, int dy, int[] consumed) {
//		CalendarView calendarView = child.getCalendarView();
//		DayView clickView = child.getClickView();
//		if (clickView == null) {
//			return;
//		}
//		int clickViewTop = clickView.getTop();
//		int clickViewHeight = clickView.getHeight();
//		float calendarViewTop = child.getCalendarView().getTop();
//		float calendarViewY = child.getCalendarView().getY();
//		//如果在最上方还在下滑
//		if (dy < 0) {
//			if (target instanceof RecyclerView) {
//				RecyclerView recyclerView = (RecyclerView)target;
//				//RecyclerView已经无法上滑时折叠日历，否则返回。
//				if (recyclerView.canScrollVertically(-1)) {
//					return;
//				}
//			}
//			//计算未完成的滑动距离
//			float surplusTop = calendarViewTop - calendarViewY;
//			float surplusBottom = calendarViewTop + calendarView.getHeight() - child.getBottom();
//
//			if (surplusTop > 0) {
//				if (surplusTop + dy > 0) {
//					calendarView.setTranslationY(calendarView.getTranslationY() - dy);
//					consumed[1] = dy;
//				} else {
//
//					consumed[1] = (int) -surplusTop;
//				}
//				calendarView.setTranslationY(calendarView.getTranslationY() - consumed[1]);
//			} else if (surplusBottom > 0) {
//				if (surplusBottom + dy > 0) {
//					consumed[1] = dy;
//				} else {
//
//					consumed[1] = (int) -surplusBottom;
//				}
//				calendarView.setTranslationY(calendarView.getTranslationY() -consumed[1]);
//				ViewCompat.offsetTopAndBottom(target, -consumed[1]);
////				child.setBottom(child.getBottom() - consumed[1]);
//			}
//		}  else if (dy > 0) {
//			//计算未完成的滑动距离
//			float surplusTop = clickViewTop - (calendarViewTop - calendarViewY);
//			float surplusBottom = child.getBottom() - (calendarViewTop + clickViewHeight);
//
//			//判断点击的View是否还在页面上。
//			if (surplusTop > 0) {
//				//这里判断选中的日期View的距离是否大于滑动的距离，防止滑动速度过快而遮挡View
//				if (surplusTop - dy > 0) {
//					surplusTop = dy;
//				}
//				//这里利用Translation移动View，而不是ViewCompat.offsetTopAndBottom，因为移动Translation View的Top不变只有Y和TranslationY改变，
//				//可以利用Top - Y计算出移动的距离
//				calendarView.setTranslationY(calendarView.getTranslationY() - surplusTop);
//				ViewCompat.offsetTopAndBottom(target, (int) - surplusTop);
////				target.setTop((int) (target.getTop() - surplusTop));
////				calendarView.setTop((int) (calendarView.getTop() - surplusTop));
//				consumed[1] = dy;
////				target.setTop((int) (target.getTop() - surplusTop));
//			} else if (surplusBottom > 0){
//				if (surplusBottom - dy > 0) {
//					//consumed[] 是使用的滑动距离，用来防止Calendar没有滑动结束时RecyclerView滑动
//					consumed[1] = dy;
//				} else {
//					consumed[1] = (int) surplusBottom;
//				}
//				ViewCompat.offsetTopAndBottom(target, -consumed[1]);
////				child.setBottom(child.getBottom() - consumed[1]);
//			}
//		}
//	}

	@Override
	public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target, float velocityX, float velocityY) {
//		Log.d(TAG, "onNestedPreFling: " + velocityY);
		child.getClickDate();
		return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
	}
}
