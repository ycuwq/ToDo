package com.yangchen.extracalendarview;

import android.support.annotation.ColorInt;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

/**
 * MonthView 的适配器
 * Created by 杨晨 on 2017/5/31.
 */
public class MonthViewAdapter extends PagerAdapter{

	private LinkedList<MonthView> mCache = new LinkedList<>();
	private SparseArray<MonthView> mViews = new SparseArray<>();

	private int count;
	private boolean isShowHoliday;
	private boolean isShowLunar;
	private int mTextSizeTop;
	private int mTextSizeBottom;
	private @ColorInt int mTextColorTop;
	private @ColorInt int mTextColorBottom;

	public MonthViewAdapter(boolean isShowHoliday, boolean isShowLunar, int textSizeTop,
	                        int textSizeBottom, int textColorTop, int textColorBottom) {
		this.isShowHoliday = isShowHoliday;
		this.isShowLunar = isShowLunar;
		this.mTextSizeTop = textSizeTop;
		this.mTextSizeBottom = textSizeBottom;
		this.mTextColorTop = textColorTop;
		this.mTextColorBottom = textColorBottom;

	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		MonthView monthView;
		if (!mCache.isEmpty()) {
			monthView = mCache.removeFirst();
		} else {
			monthView = new MonthView(container.getContext());

		}
		monthView.initAttr(isShowLunar, isShowHoliday, mTextSizeTop, mTextSizeBottom, mTextColorTop, mTextColorBottom);
		return monthView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((MonthView) object);

	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}
