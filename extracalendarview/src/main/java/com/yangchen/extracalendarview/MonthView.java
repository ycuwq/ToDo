package com.yangchen.extracalendarview;

import android.content.Context;
import android.support.v4.view.ViewPager;

/**
 * 包装ViewPager
 * 主要用来计算MonthItemView的高度
 * Created by yangchen on 2017/6/22.
 */
public class MonthView extends ViewPager {
	public MonthView(Context context) {
		super(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int calendarHeight;
		if (getAdapter() != null) {
			MonthItemView view = (MonthItemView) getChildAt(0);
			if (view != null) {
				calendarHeight = view.getMeasuredHeight();
				setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calendarHeight, MeasureSpec.EXACTLY));
			}
		}
	}
}
