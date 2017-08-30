package com.yangchen.extracalendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import com.yangchen.extracalendarview.base.Date;

import java.util.List;

/**
 * ItemView的基类，
 * Created by yangchen on 2017/6/27.
 */
public abstract class CalendarItemView extends ViewGroup {

	protected int maxRow = 6;       //最大显示的行数,maxRow可变的，如果为1为周模式，可以用来进行模式判断。

	protected static final int COLUMN = 7;        //显示的列数
	protected DayItemAttrs mDayItemAttrs;
	protected ExtraCalendarView mExtraCalendarView;
	protected List<Date> mDates;
	protected int  mLastMonthDay; //当前月份天数，上月天数，下月天数。
	protected Date mCurrentMonth;

	public CalendarItemView(ExtraCalendarView extraCalendarView, Context context) {
		super(context, null);
		mExtraCalendarView = extraCalendarView;
		mLastMonthDay = 0;
	}


	public void initAttr(DayItemAttrs dayItemAttrs) {
		mDayItemAttrs = dayItemAttrs;
		super.setBackgroundColor(mDayItemAttrs.getBackgroundColor());
	}

	public void setDates(List<Date> dates) {
		mDates = dates;
		mLastMonthDay = 0;
		if (dates.size() > 0) {
			removeAllViews();
		}

		for (int i = 0; i < dates.size(); i++) {
			Date date = dates.get(i);
			if (date.getType() == Date.TYPE_LAST_MONTH) {
				mLastMonthDay++;
			}
			final DayView view = createDayView(getContext(), date, mDayItemAttrs);
			view.setOnClickListener(v -> mExtraCalendarView.onDateClicked(view));
			//判断点击的日期是否和加载的日期是同一天如果是同一天，设置为选中的样式，
			// 这个为了解决ViewPager复用后重新创建View的点击效果消失的问题
			if (date.equals(mExtraCalendarView.getClickDate())) {
				//  周类型没有Type的概念，这里判断周的Type 是无效的。
				if (date.getType() == mExtraCalendarView.getClickDate().getType()) {
					mExtraCalendarView.onDateClicked(view);
//					mExtraCalendarView.changeDayClickedAndStyle(view);
				}
			}
			addView(view);
		}

		mCurrentMonth = dates.get(mLastMonthDay);
		requestLayout();
	}


	public Date getCurrentMonth() {
		return mCurrentMonth;
	}

	DayView getDayView(Date date) {
		//因为上月或者下月信息中的date的Type不一样，indexOf无法查找到，所以这里要重新创建。
		Date date1 = new Date(date.getYear(), date.getMonth(), date.getDay());
		date1.setType(Date.TYPE_THIS_MONTH);

		int position = mDates.indexOf(date1);

		return (DayView) getChildAt(position);
	}

	public void setClickView(Date clickView) {
		mExtraCalendarView.changeDayClickedAndStyle(getDayView(clickView));
	}

	public void setClickView(int position) {
		mExtraCalendarView.changeDayClickedAndStyle((DayView) getChildAt(position));
	}

	abstract DayView createDayView(Context context, Date date, DayItemAttrs mDayItemAttrs);

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
		int height = itemWidth * maxRow;

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

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
