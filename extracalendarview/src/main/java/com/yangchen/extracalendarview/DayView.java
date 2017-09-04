package com.yangchen.extracalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yangchen.extracalendarview.base.Date;

/**
 * 日期View
 * Created by yangchen on 2017/6/22.
 */
@SuppressLint("ViewConstructor")
public class DayView extends FrameLayout {

	private Date mDate;
	private DayItemAttrs mDayItemAttrs;
	private TextView topTv;
	private TextView bottomTv;

	//将非当月的信息设置为Bottom的颜色，与当月日期区别
	private boolean mChangeTopViewColor;

	public DayView(Context context, Date date, DayItemAttrs dayItemAttrs, boolean changeTopViewColor) {
		super(context);
		mDate = date;
		mDayItemAttrs = dayItemAttrs;
		mChangeTopViewColor = changeTopViewColor;
		initView();
	}
	/**
	 * 初始化View
	 */
	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_month_layout, this);
		topTv = (TextView) findViewById(R.id.tv_item_month_top);
		bottomTv = (TextView) findViewById(R.id.tv_item_month_bottom);

		if (mDate.getType() != Date.TYPE_THIS_MONTH && mChangeTopViewColor) {
			topTv.setTextColor(mDayItemAttrs.getTextColorBottom());
		} else {
			topTv.setTextColor(mDayItemAttrs.getTextColorTop());
		}

		bottomTv.setTextColor(mDayItemAttrs.getTextColorBottom());
		topTv.setTextSize(mDayItemAttrs.getTextSizeTop());
		bottomTv.setTextSize(mDayItemAttrs.getTextSizeBottom());
		topTv.setText(String.valueOf(mDate.getDay()));
		//设置农历数据
		if (mDayItemAttrs.isShowLunar()) {
			bottomTv.setText(mDate.getLunarDay());
		}
		//设置节日数据
		if (mDayItemAttrs.isShowHoliday()) {
			if (!TextUtils.isEmpty(mDate.getLunarHoliday())) {
				bottomTv.setText(mDate.getLunarHoliday());
			}
			if (!TextUtils.isEmpty(mDate.getHoliday())) {
				bottomTv.setText(mDate.getHoliday());
			}
		}
	}
	/**
	 * 设置点击样式或回复原状
	 * @param reset     true 恢复， false 设置点击样式
	 */
	public void setClickedViewStyle(boolean reset) {
		if (reset) {
			setBackgroundResource(0);
			//将非当月的信息设置为Bottom的颜色，与当月日期区别.
			if (mDate.getType() != Date.TYPE_THIS_MONTH && mChangeTopViewColor) {
				topTv.setTextColor(mDayItemAttrs.getTextColorBottom());
			} else {
				topTv.setTextColor(mDayItemAttrs.getTextColorTop());
			}
			bottomTv.setTextColor(mDayItemAttrs.getTextColorBottom());
		} else {
			setBackground(mDayItemAttrs.getClickBg());
			topTv.setTextColor(mDayItemAttrs.getClickTextColor());
			bottomTv.setTextColor(mDayItemAttrs.getClickTextColor());
		}
	}


	public Date getDate() {
		return mDate;
	}


}
