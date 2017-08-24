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
				Log.d(TAG, "onNestedPreScroll: ");
			}
//			} else if ((-target.getTop()) >= (calendarView.getHeight() - clickView.getHeight())) {
//				child.changeCalendarType();
//			}
		} else if (dy < 0 && child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_WEEK) {
			if (!flag) {
				int surplusTop = clickViewTop - (weekInfoBottom - calendarView.getTop());
				child.changeCalendarType();
				calendarView.scrollTo(0, surplusTop);
			}
//			ViewCompat.offsetTopAndBottom(calendarView, -dy);
//			child.changeCalendarType();
//			int surplusTop = clickViewTop - (weekInfoBottom - calendarView.getTop());
//			int surplusBottom = (calendarView.getHeight() - clickView.getHeight()) + target.getTop();
//			ViewCompat.offsetTopAndBottom(calendarView, -surplusTop);
//			ViewCompat.offsetTopAndBottom(target, -surplusBottom);
//			consumed[1] = dy;
		}
//		Log.d(TAG, "ExtraHeight: " + child.getHeight());
//		Log.d(TAG, "ExtraBottom: " + child.getBottom());
//		Log.d(TAG, "clickViewTop: " + clickViewTop);
//		Log.d(TAG, "clickViewBottom: " + clickView.getBottom());
//		Log.d(TAG, "calendarViewTop: " + calendarView.getTop());
//		Log.d(TAG, "calendarViewTY: " + calendarView.getTranslationY());
//		Log.d(TAG, "calendarViewHeight: " + calendarView.getHeight());
//		Log.d(TAG, "targetTop: " + target.getTop());
//		Log.d(TAG, "onDependentViewChanged: " + target.getHeight());

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
