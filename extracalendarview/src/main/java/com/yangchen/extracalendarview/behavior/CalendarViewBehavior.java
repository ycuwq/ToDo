package com.yangchen.extracalendarview.behavior;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import com.yangchen.extracalendarview.DayView;
import com.yangchen.extracalendarview.ExtraCalendarView;

/**
 * 控制日历响应滑动的效果
 * Created by yangchen on 2017/6/26.
 */
public class CalendarViewBehavior extends CoordinatorLayout.Behavior<ExtraCalendarView> {
	private final String TAG = getClass().getSimpleName();

	private boolean mRunning = false;       //进行变换中
	private boolean mReadyToMonth = false;

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
			if (clickViewTop > calendarView.getScrollY()) {
				int offset = Math.min(clickViewTop - calendarView.getScrollY(), dy);
				calendarView.scrollBy(0, offset);
				ViewCompat.offsetTopAndBottom(target, -offset);
			} else if (surplusBottom > 0) {

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
			consumed[1] = dy;
			Log.d(TAG, "onNestedPreScroll: " + child.getClickView().getTop());
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
				mReadyToMonth = false;
				child.setCalendarType(ExtraCalendarView.CALENDAR_TYPE_MONTH);
			}
		} else if (dy < 0 && !mRunning && child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_WEEK) {
			//FIXME 修复第一次上滑到周模式，切换时出现dy = -419，导致调用此处。
			mRunning = true;
			Log.d(TAG, "onNestedPreScroll: change to Month");
			consumed[1] = dy;
			child.changeCalendarStyle();
			calendarView.scrollTo(0, child.getClickView().getTop());
			mReadyToMonth = true;
		}
	}

	@Override
	public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, ExtraCalendarView child, View target) {
		super.onStopNestedScroll(coordinatorLayout, child, target);
		if (!mRunning) {
			return;
		}
		if (child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_MONTH && !mReadyToMonth) {
			if (child.getBottom() - target.getY() > 0 && child.getBottom() - target.getY() <100) {
				animationScrollToMonth(child, target);
			} else if (child.getBottom() - target.getY() > 0){
				animationScrollToWeek(child, target);
			}


		} else if (child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_WEEK && mReadyToMonth) {
			int surplus = (int) ((child.getBottom() - child.getClickView().getTop()) - target.getY());
			if (surplus > 0 && surplus < 100) {
				animationScrollToWeek(child, target);
			} else {
				animationScrollToMonth(child, target);
			}
//			calendarView.scrollTo(0, 0);
		}
	}

	private void animationScrollToWeek(ExtraCalendarView child, View target) {
		int surplusTop = child.getClickView().getTop() - child.getCalendarLayout().getScrollY();
		int surplusBottom = target.getTop() - (child.getCalendarLayout().getTop() + child.getClickView().getHeight());
		int finishedTop = child.getCalendarLayout().getScrollY();
		final OverScroller scroller = new OverScroller(target.getContext());
		scroller.startScroll(0, 0,
				0, surplusTop + surplusBottom, 2000);
		ViewCompat.postOnAnimation(child, new Runnable() {
			@Override
			public void run() {
				int detairBottom = target.getTop() - (child.getCalendarLayout().getTop() + child.getClickView().getHeight());
				if (scroller.computeScrollOffset()) {
					int currentY = scroller.getCurrY();
					int clickViewTop = child.getClickView().getTop();
					if (detairBottom > 0) {
						int offsetTop = currentY + finishedTop > clickViewTop ? clickViewTop : currentY + finishedTop;
						child.getCalendarLayout().scrollTo(0, offsetTop);
						int offsetBottom = Math.min(-(surplusBottom - detairBottom - currentY), detairBottom);
						ViewCompat.offsetTopAndBottom(target, -offsetBottom);
						ViewCompat.postOnAnimation(child, this);
					} else {
						if (child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_MONTH) {
							child.changeCalendarType();
						}
						child.getCalendarLayout().scrollTo(0, 0);
						mRunning = false;
						mReadyToMonth = false;
					}

				}
			}
		});
	}

	private void animationScrollToMonth(ExtraCalendarView child, View target) {
		int surplusBottom = (int) (child.getBottom() - target.getY());
		final OverScroller scroller = new OverScroller(target.getContext());
		scroller.startScroll(0, 0,
				0, (int) (child.getBottom() - target.getY()), 1000);
		ViewCompat.postOnAnimation(child, new Runnable() {
			@Override
			public void run() {
				if (scroller.computeScrollOffset()) {
					int currentY = scroller.getCurrY();
					int offsetBottomNow = (int) (child.getBottom() - target.getY());
					if (offsetBottomNow > 0) {
						int offset = offsetBottomNow - surplusBottom + currentY;
						int surplusTopNow = (int) (target.getY() - (child.getBottom() - child.getClickView().getTop()));
						if (surplusTopNow + offset > 0 && surplusTopNow < 0) {
							int shouldOffset = surplusTopNow + offset;
							child.getCalendarLayout().scrollBy(0, -shouldOffset);
						} else if (surplusTopNow > 0) {
							child.getCalendarLayout().scrollBy(0, -offset);
						}
						ViewCompat.offsetTopAndBottom(target, offset);
						ViewCompat.postOnAnimation(child, this);
					} else {
						if (!mReadyToMonth) {
							return;
						}
						mRunning = false;
						mReadyToMonth = false;
						child.setCalendarType(ExtraCalendarView.CALENDAR_TYPE_MONTH);
					}

				}
			}
		});
	}

	@Override
	public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ExtraCalendarView child, @NonNull View target, float velocityX, float velocityY) {
		return mRunning;
	}

}
