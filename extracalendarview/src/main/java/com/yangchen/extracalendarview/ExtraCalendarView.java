package com.yangchen.extracalendarview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by 杨晨 on 2017/5/27.
 */
public class ExtraCalendarView extends ViewPager {
	public ExtraCalendarView(Context context) {
		super(context);
	}

	public ExtraCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	private void initAttr(AttributeSet attrs) {

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}



}
