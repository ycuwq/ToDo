package com.yangchen.extracalendarview.behavior;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
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

	/**
	 * 进行变化的滑动距离
	 */
	private final int CHANGE_SPIL_BOUNDARY = 100;
	/**
	 * 	进行变换中
	 */
	private boolean mIsRunning = false;

	/**
	 * 是否正在变换成月
	 * 从week模式变换成month模式时，先显示Month的View，等到Month的View完全显示出来时才切换成Month。此flag用来标记这段时间。
	 *
	 */
	private boolean mIsReadyToMonth = false;

	/**
	 * 用来标记当由动画来完成滚动的这段时间
	 */
	private boolean mIsAnimationScroll = false;

	@Override
	public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ExtraCalendarView child, @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes, int type) {
		final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;

		return started;
	}

	@Override
	public boolean onInterceptTouchEvent(CoordinatorLayout parent, ExtraCalendarView child, MotionEvent ev) {
		//当正在进行切换模式的动画时，拦截用户其他操作。避免模式还未切换完成时用户切换日期而出现问题。
		return mIsAnimationScroll;
	}

	@Override
	public boolean onTouchEvent(CoordinatorLayout parent, ExtraCalendarView child, MotionEvent ev) {
		return super.onTouchEvent(parent, child, ev);
//		return true;
	}

	@Override
	public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ExtraCalendarView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
		super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
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
		if (dy > 0 && (child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_MONTH || mIsRunning)) {
			//上滑的处理事项
			consumed[1] = dy;
			if (!mIsRunning && child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_MONTH) {
				//当变成周模式在切换成月模式时，会导致RecyclerView坐标异常，当上滑时重新layout一下可以恢复
				mIsRunning = true;
			}
			//点击View上方距离顶部的距离
			int clickViewTop = clickView.getTop();
			//clickView下方应该收缩的距离
			int surplusBottom = target.getTop() - (calendarView.getTop() + clickView.getHeight());
			if (clickViewTop > calendarView.getScrollY()) {
				int offset = Math.min(clickViewTop - calendarView.getScrollY(), dy);
				calendarView.scrollBy(0, offset);
				ViewCompat.offsetTopAndBottom(target, -offset);
			} else if (surplusBottom > 0) {

				int offset = Math.min(surplusBottom, dy);
				ViewCompat.offsetTopAndBottom(target, -offset);
			} else if (clickViewTop <= calendarView.getScrollY() && surplusBottom<=0) {
				child.setCalendarType(ExtraCalendarView.CALENDAR_TYPE_WEEK);
				calendarView.scrollTo(0, 0);

				mIsRunning = false;
			}

		} else if (dy < 0 && mIsRunning) {
			consumed[1] = dy;
			int surplus = child.getBottom() - child.getClickView().getTop();
			if (surplus > target.getY()) {
				int offset = (int) Math.min(surplus - target.getY(), -dy);
				ViewCompat.offsetTopAndBottom(target, offset);
			} else if (calendarView.getScrollY() > 0) {
				int offset = Math.min(calendarView.getScrollY(), -dy);
				calendarView.scrollBy(0, -offset);
				ViewCompat.offsetTopAndBottom(target, offset);
			} else {
				mIsRunning = false;
				mIsReadyToMonth = false;
				child.setCalendarType(ExtraCalendarView.CALENDAR_TYPE_MONTH);
			}
		} else if (dy < 0 && !mIsRunning && child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_WEEK) {
			mIsRunning = true;
			consumed[1] = dy;
			child.changeCalendarStyle();
			calendarView.scrollTo(0, child.getClickView().getTop());
			mIsReadyToMonth = true;
		}
	}


	@Override
	public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ExtraCalendarView child, @NonNull View target, int type) {
		super.onStopNestedScroll(coordinatorLayout, child, target, type);
		if (!mIsRunning) {
			return;
		}

		if (child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_MONTH && !mIsReadyToMonth) {
			if (child.getBottom() - target.getY() > 0 && child.getBottom() - target.getY() < CHANGE_SPIL_BOUNDARY) {
				animationScrollToMonth(child, target);
			} else if (child.getBottom() - target.getY() > 0){
				animationScrollToWeek(child, target);
			}


		} else if (child.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_WEEK && mIsReadyToMonth) {
			//  （总滑动距离） - （剩余滑动距离）
			int surplus = (int) ((child.getCalendarLayout().getHeight() - child.getClickView().getHeight()) - (child.getBottom() - target.getY()));
			if (Math.abs(surplus) < CHANGE_SPIL_BOUNDARY) {
				animationScrollToWeek(child, target);
			} else {
				animationScrollToMonth(child, target);
			}
		}
	}


	private void animationScrollToWeek(ExtraCalendarView child, View target) {
		mIsAnimationScroll = true;
		int surplusTop = child.getClickView().getTop() - child.getCalendarLayout().getScrollY();
		int surplusBottom = target.getTop() - (child.getCalendarLayout().getTop() + child.getClickView().getHeight());
		int finishedTop = child.getCalendarLayout().getScrollY();
		final OverScroller scroller = new OverScroller(target.getContext());
		scroller.startScroll(0, 0,
				0, surplusTop + surplusBottom, 1000);
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
						child.setCalendarType(ExtraCalendarView.CALENDAR_TYPE_WEEK);
						child.getCalendarLayout().scrollTo(0, 0);
						mIsRunning = false;
						mIsReadyToMonth = false;
						mIsAnimationScroll = false;
					}

				}
			}
		});
	}

	private void animationScrollToMonth(ExtraCalendarView child, View target) {
		mIsAnimationScroll = true;
		int surplusBottom = (int) (child.getBottom() - target.getY());
		final OverScroller scroller = new OverScroller(target.getContext());
		scroller.startScroll(0, 0,
				0, (int) (child.getBottom() - target.getY()), 500);
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
						mIsAnimationScroll = false;
						if (!mIsReadyToMonth) {
							return;
						}
						mIsRunning = false;
						mIsReadyToMonth = false;
						child.setCalendarType(ExtraCalendarView.CALENDAR_TYPE_MONTH);
					}

				}
			}
		});
	}

	@Override
	public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ExtraCalendarView child, @NonNull View target, float velocityX, float velocityY) {
		return mIsRunning;
	}

}
