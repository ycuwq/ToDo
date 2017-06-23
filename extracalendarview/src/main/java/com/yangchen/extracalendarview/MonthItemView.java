package com.yangchen.extracalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 自定义扩展的CalendarView
 * Created by 杨晨 on 2017/5/11.
 */
@SuppressLint("ViewConstructor")
class MonthItemView extends ViewGroup {

	private static final int MAX_ROW = 6;       //最大显示的行数
	private static final int COLUMN = 7;        //显示的列数

	private int mCurrentMonthDay, mLastMonthDay, mNextMonthDay; //当前月份天数，上月天数，下月天数。
	private Date mCurrentMonth;

	private DayItemAttrs mDayItemAttrs;
	private ExtraCalendarView mExtraCalendarView;
	private List<Date> mDates;
	public MonthItemView(ExtraCalendarView extraCalendarView, @NonNull Context context) {
		super(context, null);
		mExtraCalendarView = extraCalendarView;
	}

	public void initAttr(DayItemAttrs dayItemAttrs) {
		mDayItemAttrs = dayItemAttrs;
		super.setBackgroundColor(mDayItemAttrs.getBackgroundColor());
	}

	public void setDates(List<Date> dates, int currentMonthDays) {
		mDates = dates;
		if (dates.size() > 0) {
			removeAllViews();
		}
		mCurrentMonthDay = currentMonthDays;
		mLastMonthDay = 0;
		mNextMonthDay = 0;
		for (int i = 0; i < dates.size(); i++) {
			Date date = dates.get(i);
			final DayView view = new DayView(getContext(), date, mDayItemAttrs);
			view.setOnClickListener(v -> mExtraCalendarView.onDateClicked(view));
			if (date.equals(mExtraCalendarView.getClickDate())) {
				mExtraCalendarView.changeDayClickedStyle(view);
			}
			addView(view);
		}
		mCurrentMonth = dates.get(mLastMonthDay);
		requestLayout();
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (getChildCount() == 0) {
			return;
		}

		View childView = getChildAt(0);
		int itemWidth = childView.getMeasuredWidth();
		int itemHeight = childView.getMeasuredHeight();

		//当显示五行时扩大行间距
		int dy = 0;
		if (getChildCount() == 35) {
			dy = itemWidth / 5;
		}

		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			int left = i % COLUMN * itemWidth;// + (2 * (i % COLUMN) + 1) * 7
			int top = i / COLUMN * (itemHeight + dy);
			int right = left + itemWidth;
			int bottom = top + itemHeight;
			view.layout(left, top, right, bottom);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

		int itemWidth = widthSpecSize / COLUMN;// - 14

		int width = widthSpecSize;
		int height = itemWidth * MAX_ROW;

		setMeasuredDimension(width, height);

		int itemHeight;

//        if (getChildCount() == 35) {
//	        itemHeight = getMeasuredHeight() / 5;
//        } else {
		itemHeight = itemWidth;
//        }

		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			childView.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
		}
	}


	public Date getCurrentMonth() {
		return mCurrentMonth;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}


	DayView getDayView(Date date) {
		//因为上月或者下月信息中的date的Type不一样，indexOf无法查找到，所以这里要重新创建。
		Date date1 = new Date(date.getYear(), date.getMonth(), date.getDay());
		date1.setType(Date.TYPE_THIS_MONTH);

		int position = mDates.indexOf(date1);

		return (DayView) getChildAt(position);
	}
}
