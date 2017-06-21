package com.yangchen.extracalendarview;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.yangchen.extracalendarview.util.CalendarUtil;
import com.yangchen.extracalendarview.util.SolarUtil;

import java.util.LinkedList;

/**
 * MonthView 的适配器
 * Created by 杨晨 on 2017/5/31.
 */
public class MonthViewAdapter extends PagerAdapter{

	private LinkedList<MonthItemView> mCache = new LinkedList<>();
	private SparseArray<MonthItemView> mViews = new SparseArray<>();

	private int mCount;     //一共显示多少个月

	private int mStartYear, mStartMonth;
	private DayItemAttrs mDayItemAttrs;

	MonthViewAdapter(int count, int startYear, int startMonth, DayItemAttrs dayItemAttrs) {
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
	public Object instantiateItem(ViewGroup container, int position) {
		MonthItemView monthItemView;
		if (!mCache.isEmpty()) {
			monthItemView = mCache.removeFirst();
		} else {
			monthItemView = new MonthItemView(container.getContext());

		}

		int date[] = CalendarUtil.positionToDate(position, mStartYear, mStartMonth);
		monthItemView.initAttr(mDayItemAttrs);
		monthItemView.setDates(CalendarUtil.getDates(date[0], date[1]), SolarUtil.getMonthDays(date[0], date[1]));
		mViews.put(position, monthItemView);
		container.addView(monthItemView);
		return monthItemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((MonthItemView) object);
		mCache.addLast((MonthItemView) object);
		mViews.remove(position);
	}

	//TODO 处理一下作用域
	public MonthItemView getItem(int position) {
		MonthItemView view = mViews.get(position);
		return view;
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}
