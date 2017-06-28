package com.yangchen.extracalendarview;

import android.view.ViewGroup;

import com.yangchen.extracalendarview.util.CalendarUtil;

/**
 * MonthView 的适配器
 * Created by 杨晨 on 2017/5/31.
 */
public class MonthViewAdapter extends CalendarAdapter<MonthItemView> {

	private int mCount;     //一共显示多少个月

	private int mStartYear, mStartMonth;
	private DayItemAttrs mDayItemAttrs;
	private ExtraCalendarView mExtraCalendarView;

	MonthViewAdapter(ExtraCalendarView extraCalendarView, int count, int startYear, int startMonth, DayItemAttrs dayItemAttrs) {
		mExtraCalendarView = extraCalendarView;
		mCount = count;
		mStartYear = startYear;
		mStartMonth = startMonth;
		mDayItemAttrs = dayItemAttrs;
	}

	void setStartDate(int startYear, int startMonth, int count) {
		mCount = count;
		mStartYear = startYear;
		mStartMonth = startMonth;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mCount;
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		MonthItemView monthItemView;
		if (!mCache.isEmpty()) {
			monthItemView = mCache.removeFirst();
		} else {
			monthItemView = new MonthItemView(mExtraCalendarView, container.getContext());

		}

		int date[] = CalendarUtil.positionToDate(position, mStartYear, mStartMonth);
		monthItemView.initAttr(mDayItemAttrs);
		monthItemView.setDates(CalendarUtil.getMonthDates(date[0], date[1]));
		mViews.put(position, monthItemView);
		container.addView(monthItemView);
		return monthItemView;
	}
}
