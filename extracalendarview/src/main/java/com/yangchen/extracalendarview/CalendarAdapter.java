package com.yangchen.extracalendarview;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

/**
 * Created by yangchen on 2017/6/27.
 */
abstract class CalendarAdapter<V extends CalendarItemView> extends PagerAdapter {

	LinkedList<V> mCache = new LinkedList<>();
	SparseArray<V> mViews = new SparseArray<>();

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((V) object);
		mCache.addLast((V) object);
		mViews.remove(position);
	}

	//TODO 处理一下作用域
	public V getItem(int position) {
		return mViews.get(position);
	}


	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	abstract void setStartDate(int startYear, int startMonth, int count);
}
