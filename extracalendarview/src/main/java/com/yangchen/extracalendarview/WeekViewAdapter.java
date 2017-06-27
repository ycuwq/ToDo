package com.yangchen.extracalendarview;

import android.view.ViewGroup;

import com.yangchen.extracalendarview.util.CalendarUtil;

/**
 * Created by yangchen on 2017/6/27.
 */
public class WeekViewAdapter extends BaseCalendarAdapter<WeekItemView> {

	private int mCount;     //一共显示多少个星期

	private int mStartYear, mStartMonth;
	private DayItemAttrs mDayItemAttrs;
	private ExtraCalendarView mExtraCalendarView;

	WeekViewAdapter(ExtraCalendarView extraCalendarView, int count, int startYear, int startMonth, DayItemAttrs dayItemAttrs) {
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
		WeekItemView weekItemView;
		if (!mCache.isEmpty()) {
			weekItemView = mCache.removeFirst();
		} else {
			weekItemView = new WeekItemView(mExtraCalendarView, container.getContext());

		}
		weekItemView.initAttr(mDayItemAttrs);
		weekItemView.setDates(CalendarUtil.getWeekDays(mStartYear, mStartMonth, position));
		mViews.put(position, weekItemView);
		container.addView(weekItemView);
		return weekItemView;
	}
}
