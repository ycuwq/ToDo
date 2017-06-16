package com.yangchen.extracalendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义扩展的CalendarView
 * Created by 杨晨 on 2017/5/11.
 */
public class MonthItemView extends ViewGroup {

	private static final int MAX_ROW = 6;       //最大显示的行数
	private static final int COLUMN = 7;        //显示的列数

	private boolean mShowLunar;
	private boolean mShowHoliday;
	private int mTextSizeTop;
	private int mTextSizeBottom;
	private @ColorInt int mTextColorTop;
	private @ColorInt int mTextColorBottom;
	private @ColorInt int mBackgroundColor;

	private int mCurrentMonthDay, mLastMonthDay, mNextMonthDay; //当前月份天数，上月天数，下月天数。
	private Date mCurrentMonth;
	private Date mCurrentDay;
	public MonthItemView(@NonNull Context context) {
		this(context, null);
	}
	public MonthItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MonthItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void initAttr(boolean isShowLunar, boolean isShowHoliday, int textSizeTop, int textSizeBottom,
	                      int textColorTop, int textColorBottom, int backgroundColor) {
		mShowLunar = isShowLunar;
		mShowHoliday = isShowHoliday;
		mTextSizeTop = textSizeTop;
		mTextSizeBottom = textSizeBottom;
		mTextColorTop = textColorTop;
		mTextColorBottom = textColorBottom;
		mBackgroundColor = backgroundColor;
		super.setBackgroundColor(mBackgroundColor);
	}

	public void setDates(List<Date> dates, int currentMonthDays) {
		if (dates.size() > 0) {
			removeAllViews();
		}
		mCurrentMonthDay = currentMonthDays;
		mLastMonthDay = 0;
		mNextMonthDay = 0;
		for (int i = 0; i < dates.size(); i ++) {
			Date date = dates.get(i);
			switch (date.getType()) {
				case Date.TYPE_LAST_MONTH:
					mLastMonthDay++;
					addView(new View(getContext()), i);
					continue;
				case Date.TYPE_NEXT_MONTH:
					addView(new View(getContext()), i);
					continue;
				default:
					break;
			}
			View view = LayoutInflater.from(getContext()).inflate(R.layout.item_month_layout, null);
			TextView topTv = (TextView) view.findViewById(R.id.tv_item_month_top);
			TextView bottomTv = (TextView) view.findViewById(R.id.tv_item_month_bottom);
			topTv.setTextColor(mTextColorTop);
			bottomTv.setTextColor(mTextColorBottom);
			topTv.setTextSize(mTextSizeTop);
			bottomTv.setTextSize(mTextSizeBottom);
			topTv.setText(String.valueOf(date.getDay()));
			//设置农历数据
			if (mShowLunar) {
				bottomTv.setText(date.getLunarDay());
			}
			//设置节日数据
			if (mShowHoliday) {
				if (!TextUtils.isEmpty(date.getLunarHoliday())) {
					bottomTv.setText(date.getLunarHoliday());
				}
				if (!TextUtils.isEmpty(date.getHoliday())) {
					bottomTv.setText(date.getHoliday());
				}
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
}
