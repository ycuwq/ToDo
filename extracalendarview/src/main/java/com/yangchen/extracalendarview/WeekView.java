package com.yangchen.extracalendarview;

import android.content.Context;
import android.support.v4.view.ViewPager;

/**
 *  包装ViewPager
 * 主要用来计算WeekItemView的高度
 * Created by yangchen on 2017/6/27.
 */
public class WeekView extends ViewPager {
	public WeekView(Context context) {
		super(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int calendarHeight;
		if (getAdapter() != null) {
			WeekItemView view = (WeekItemView) getChildAt(0);
			if (view != null) {
				calendarHeight = view.getMeasuredHeight();
				setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calendarHeight, MeasureSpec.EXACTLY));
			}
		}
	}
}
