package com.yangchen.extracalendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CalendarView;

/**
 * 自定义扩展的CalendarView
 * Created by 杨晨 on 2017/5/11.
 */
public class ExtraCalendarView extends CalendarView {

	private static final int MAX_ROW = 6;       //最大显示的行数
	private static final int COLUMN = 7;        //显示的列数

	public ExtraCalendarView(@NonNull Context context) {
		super(context);
	}
	public ExtraCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public ExtraCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
