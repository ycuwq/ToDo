package com.yangchen.extracalendarview.listener;

import android.view.View;

import com.yangchen.extracalendarview.Date;

/**
 * 当日期点击时调用
 * Created by yangchen on 2017/6/21.
 */
public interface OnDayClickListener {
	void onClick(View v, Date date);
}
