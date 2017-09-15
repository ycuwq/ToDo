package com.yangchen.extracalendarview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.yangchen.extracalendarview.base.Date;

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
	private ExtraCalendarView mExtraCalendarView;

	CalendarAdapter(ExtraCalendarView extraCalendarView, int count, int startYear, int startMonth, DayItemAttrs dayItemAttrs) {
		mExtraCalendarView = extraCalendarView;
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
			calendarItemView = instantiateCalendarView(mExtraCalendarView, container.getContext());

		}
		calendarItemView.initAttr(mDayItemAttrs);
		calendarItemView.setDates(getCalendarDates(mStartYear, mStartMonth, position));
		mViews.put(position, calendarItemView);
		container.addView(calendarItemView);
		return calendarItemView;
	}

	public abstract CalendarItemView instantiateCalendarView(ExtraCalendarView extraCalendarView, Context context);

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
	public void setClickDate(Date clickDate) {
		CalendarItemView calendarItemView = mViews.get(mViews.keyAt(1));
		calendarItemView.setClickView(clickDate);

	}

	@Override
	public int getCount() {
		return mCount;
	}
}
