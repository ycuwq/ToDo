package com.yangchen.extracalendarview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * MonthView 的适配器
 * Created by 杨晨 on 2017/5/31.
 */
public class MonthViewAdapter extends PagerAdapter{

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);

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
