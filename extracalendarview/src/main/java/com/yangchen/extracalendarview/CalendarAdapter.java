package com.yangchen.extracalendarview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.yangchen.extracalendarview.base.Date;
import com.yangchen.extracalendarview.listener.OnDayViewClickListener;

import java.util.LinkedList;
import java.util.List;

/**
 * 日历用的Adapter{@link CalendarItemView}
 * Created by yangchen on 2017/6/27.
 */
abstract class  CalendarAdapter extends PagerAdapter {
	private final String TAG = getClass().getSimpleName();

	private LinkedList<CalendarItemView> mCache = new LinkedList<>();
	private SparseArray<CalendarItemView> mViews = new SparseArray<>();

	private int mCount;     //一共显示多少个月


	private int mStartYear, mStartMonth;
	private DayItemAttrs mDayItemAttrs;
	private OnDayViewClickListener mDayViewClickListener;

	CalendarAdapter(OnDayViewClickListener onDayViewClickListener, int count, int startYear, int startMonth, DayItemAttrs dayItemAttrs) {
		mDayViewClickListener = onDayViewClickListener;
		mCount = count;
		mStartYear = startYear;
		mStartMonth = startMonth;
		mDayItemAttrs = dayItemAttrs;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		CalendarItemView calendarItemView;
		if (!mCache.isEmpty()) {
			calendarItemView = mCache.removeFirst();
		} else {
			calendarItemView = instantiateCalendarView(mDayViewClickListener, container.getContext());

		}
		calendarItemView.initAttr(mDayItemAttrs);
		calendarItemView.setDates(getCalendarDates(mStartYear, mStartMonth, position));
		mViews.put(position, calendarItemView);
		container.addView(calendarItemView);
		return calendarItemView;
	}

	public abstract CalendarItemView instantiateCalendarView(OnDayViewClickListener onDayViewClickListener, Context context);

	public abstract List<Date> getCalendarDates(int startYear, int startMonth, int position);

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
		mCache.addLast((CalendarItemView) object);
		mViews.remove(position);

	}


	//TODO 处理一下作用域
	public CalendarItemView getItem(int position) {
		return mViews.get(position);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	void setStartDate(int startYear, int startMonth, int count) {
		mCount = count;
		mStartYear = startYear;
		mStartMonth = startMonth;
		notifyDataSetChanged();
	}

	/**
	 * 找到ClickView所在的CalendarItemView 并设置点击事件
	 * @param clickDate
	 */
	public void setClickedView(Date clickDate) {
		//设置的pager的缓存数为1个，当前页在中间，
		CalendarItemView calendarItemView = mViews.get(mViews.keyAt(1));
		if (calendarItemView == null) {
			return;
		}
		DayView dayView = calendarItemView.getDayView(clickDate);
		if (dayView == null) {
			//当Pager快速滑动时，由于SparseArray的Delete的机制，可能还没有销毁，目标在倒数第二个。
			if (mViews.size() > 3) {
				calendarItemView = mViews.get(mViews.keyAt(mViews.size() - 2));
			} else if (mViews.size() < 3) {
				calendarItemView = mViews.get(mViews.keyAt(0));
			}
		}
		calendarItemView.setClickedView(clickDate);

	}

	@Override
	public int getCount() {
		return mCount;
	}
}
