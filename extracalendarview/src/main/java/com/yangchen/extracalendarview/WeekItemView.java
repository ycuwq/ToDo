package com.yangchen.extracalendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * 显示一周的日期
 * Created by yangchen on 2017/6/27.
 */
public class WeekItemView extends BaseItemView {

	private static final int MAX_ROW = 1;       //最大显示的行数

	public WeekItemView(ExtraCalendarView extraCalendarView, Context context) {
		super(extraCalendarView, context);
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


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}


}
