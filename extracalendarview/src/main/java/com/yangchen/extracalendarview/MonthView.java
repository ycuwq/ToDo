package com.yangchen.extracalendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 自定义扩展的CalendarView
 * Created by 杨晨 on 2017/5/11.
 */
public class MonthView extends ViewGroup {

	private static final int MAX_ROW = 6;       //最大显示的行数
	private static final int COLUMN = 7;        //显示的列数

	private Context mContext;

	private boolean mShowLunar;
	private boolean mShowHoliday;
	private int mTextSizeTop;
	private int mTextSizeBottom;
	private @ColorInt int mTextColorTop;
	private @ColorInt int mTextColorBottom;

	private int mCurrentMonthDay, mLastMonthDay, mNextMonthDay; //当前月份天数，上月天数，下月天数。

	public MonthView(@NonNull Context context) {
		this(context, null);
	}
	public MonthView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MonthView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		initAttr(attrs);
	}

	private void initAttr(AttributeSet attrs) {
	}

	public void setDates(List<Date> dates, int currentMonthDays) {
		if (dates.size() > 0) {
			removeAllViews();
		}
		mCurrentMonthDay = currentMonthDays;
		mLastMonthDay = 0;
		mNextMonthDay = 0;
		for (Date date : dates) {
			switch (date.getType()) {
				case Date.TYPE_LAST_MONTH:
					mLastMonthDay++;
					break;
				case Date.TYPE_NEXT_MONTH:
					mNextMonthDay++;
					break;
				default:
					break;
			}
			addView(new View(getContext()));
		}
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
//            itemHeight = getMeasuredHeight() / 5;
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
