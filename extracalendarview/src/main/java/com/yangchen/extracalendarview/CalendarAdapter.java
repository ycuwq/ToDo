package com.yangchen.extracalendarview;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.yangchen.extracalendarview.util.Annotations;
import com.yangchen.extracalendarview.util.CalendarUtil;

import java.util.LinkedList;

/**
 * 日历用的Adapter{@link CalendarItemView}
 * Created by yangchen on 2017/6/27.
 */
class CalendarAdapter extends PagerAdapter {
	private final String TAG = getClass().getSimpleName();

	private LinkedList<CalendarItemView> mCache = new LinkedList<>();
	private SparseArray<CalendarItemView> mViews = new SparseArray<>();

	private int mCount;     //一共显示多少个月

	private @Annotations.CalendarType
	int mCalendarType = ExtraCalendarView.CALENDAR_TYPE_MONTH;        //显示的日历类型，显示一周，或者一月

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
			calendarItemView = new CalendarItemView(mExtraCalendarView, container.getContext());

		}
		calendarItemView.initAttr(mDayItemAttrs);

		if (mCalendarType == ExtraCalendarView.CALENDAR_TYPE_WEEK) {
			calendarItemView.setDates(CalendarUtil.getWeekDaysForPosition(mStartYear, mStartMonth,1, position));
		} else {
			int date[] = CalendarUtil.positionToDate(position, mStartYear, mStartMonth);
			calendarItemView.setDates(CalendarUtil.getMonthDates(date[0], date[1]));
		}

		mViews.put(position, calendarItemView);
		container.addView(calendarItemView);
		return calendarItemView;
	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
		mCache.addLast((CalendarItemView) object);
		mViews.remove(position);
	}

	public void setCalendarType(@Annotations.CalendarType int type) {
		mCalendarType = type;
	}

	public int getCalendarType() {
		return mCalendarType;
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

	@Override
	public int getCount() {
		return mCount;
	}
}
