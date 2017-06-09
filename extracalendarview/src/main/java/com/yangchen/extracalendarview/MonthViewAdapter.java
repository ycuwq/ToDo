package com.yangchen.extracalendarview;

import android.support.annotation.ColorInt;
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

	private int count;
	private boolean isShowHoliday;
	private boolean isShowLunar;
	private int mTextSizeTop;
	private int mTextSizeBottom;
	private @ColorInt int mTextColorTop;
	private @ColorInt int mTextColorBottom;
	private int mStartYear, mStartMonth;

	public MonthViewAdapter(int startYear, int startMonth, boolean isShowHoliday, boolean isShowLunar, int textSizeTop,
	                        int textSizeBottom, int textColorTop, int textColorBottom) {
		mStartYear = startYear;
		mStartMonth = startMonth;
		this.isShowHoliday = isShowHoliday;
		this.isShowLunar = isShowLunar;
		this.mTextSizeTop = textSizeTop;
		this.mTextSizeBottom = textSizeBottom;
		this.mTextColorTop = textColorTop;
		this.mTextColorBottom = textColorBottom;
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
		monthItemView.initAttr(isShowLunar, isShowHoliday, mTextSizeTop, mTextSizeBottom, mTextColorTop, mTextColorBottom);
		monthItemView.setDates(CalendarUtil.getDates(date[0], date[1]), SolarUtil.getMonthDays(date[0], date[1]));
		mViews.put(position, monthItemView);
		container.addView(monthItemView);
		return monthItemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((MonthItemView) object);

	}

	@Override
	public int getCount() {
		return 12;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}
