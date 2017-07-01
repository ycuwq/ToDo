package com.yangchen.extracalendarview;

import android.content.Context;
import android.view.ViewGroup;

import com.yangchen.extracalendarview.base.Date;

import java.util.List;

/**
 * ItemView的基类，
 * Created by yangchen on 2017/6/27.
 */
public abstract class CalendarItemView extends ViewGroup {
	protected static final int COLUMN = 7;        //显示的列数
	protected DayItemAttrs mDayItemAttrs;
	protected ExtraCalendarView mExtraCalendarView;
	protected List<Date> mDates;
	protected int  mLastMonthDay; //当前月份天数，上月天数，下月天数。
	protected Date mCurrentMonth;

	public CalendarItemView(ExtraCalendarView extraCalendarView, Context context) {
		super(context);
		mExtraCalendarView = extraCalendarView;
		mLastMonthDay = 0;
	}

	public void initAttr(DayItemAttrs dayItemAttrs) {
		mDayItemAttrs = dayItemAttrs;
		super.setBackgroundColor(mDayItemAttrs.getBackgroundColor());
	}

	public void setDates(List<Date> dates) {
		mDates = dates;
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
				mExtraCalendarView.changeDayClickedAndStyle(view);
			}
			addView(view);
		}
		mCurrentMonth = dates.get(mLastMonthDay);
		requestLayout();
	}

	abstract DayView createDayView(Context context, Date date, DayItemAttrs mDayItemAttrs);

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
}
