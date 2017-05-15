package com.yangchen.extracalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by 杨晨 on 2017/5/15.
 */
public class WeekView extends View {

	private Context mContext;
	private Paint mPaint;
	private @ColorInt int mTextColor = Color.WHITE;
	private @ColorInt int mBackgroundColor = Color.BLUE;
	private int mTextSize = 12;
	public WeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		initAttrs(attrs);
		initPaint();
	}

	private void initAttrs(AttributeSet attrs) {
		TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.WeekView);
		for (int i = 0; i < a.getIndexCount(); i++) {
			int attr = a.getIndex(i);
			if (attr == R.styleable.WeekView_backgroundColor) {
				mBackgroundColor = a.getColor(i, mBackgroundColor);
			} else if (attr == R.styleable.WeekView_textSize) {
				mTextSize = a.getInteger(i, mTextSize);
			} else if (attr == R.styleable.WeekView_textColor) {
				mTextColor = a.getColor(i, mTextColor);
			}
		}
		a.recycle();
	}
	private void initPaint() {
		mPaint = new Paint();
		mPaint.setTextSize(mTextSize);
		mPaint.setColor(mTextColor);
		//抗锯齿
		mPaint.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		//TODO 适配wrap_content
//		int width;
//		int height;
//		if (widthMode == MeasureSpec.AT_MOST) {
//			width = MeasureSpec.makeMeasureSpec((1 << 30) -1, MeasureSpec.AT_MOST);
//		}
//		if (heightMode == MeasureSpec.AT_MOST) {
//			height = MeasureSpec.makeMeasureSpec((1 << 30) -1, MeasureSpec.AT_MOST);
//		}
		setMeasuredDimension(widthSize, heightSize);
	}
}
