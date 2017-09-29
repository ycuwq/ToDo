package com.yangchen.extracalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.yangchen.extracalendarview.base.Date;
import com.yangchen.extracalendarview.util.DensityUtil;

/**
 * 日期View
 * Created by yangchen on 2017/6/22.
 */
@SuppressLint("ViewConstructor")
public class DayView extends View {

	private Date mDate;
	private DayItemAttrs mDayItemAttrs;
	private TextPaint mTextPaint;
	//将非当月的信息设置为Bottom的颜色，与当月日期区别
	private boolean mChangeTopViewColor;
	private String mTopText, mBottomText;
	private float mTopTextSize, mBottomTextSize;
	private int mTopTextColor, mBottomTextColor;
	float mTop;
	float mBottom;
	public DayView(Context context, Date date, DayItemAttrs dayItemAttrs, boolean changeTopViewColor) {
		super(context);
		mDate = date;
		mDayItemAttrs = dayItemAttrs;
		mChangeTopViewColor = changeTopViewColor;

		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTopText = String.valueOf(date.getDay());
		Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
		mTop = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
		mBottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
		mTopTextSize = DensityUtil.sp2px(getContext(), dayItemAttrs.getTextSizeTop());
		mBottomTextSize = DensityUtil.sp2px(getContext(), dayItemAttrs.getTextSizeBottom());
		if (dayItemAttrs.isShowLunar()) {
			mBottomText = mDate.getLunarDay();
		}
		//设置节日数据
		if (dayItemAttrs.isShowHoliday()) {
			if (!TextUtils.isEmpty(mDate.getLunarHoliday())) {
				mBottomText = mDate.getLunarHoliday();
			}
			if (!TextUtils.isEmpty(mDate.getHoliday())) {
				mBottomText = mDate.getHoliday();
			}
		}
		if (mChangeTopViewColor && mDate.getType() != Date.TYPE_THIS_MONTH) {
			mTopTextColor = dayItemAttrs.getTextColorBottom();
		} else {
			mTopTextColor = dayItemAttrs.getTextColorTop();
		}

		mBottomTextColor = dayItemAttrs.getTextColorBottom();

	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mTextPaint.setTextSize(mTopTextSize);
		mTextPaint.setColor(mTopTextColor);

		int baseLineY = (int) (canvas.getHeight() / 2 - mTop / 2 - mBottom / 2);
		canvas.drawText(mTopText, canvas.getWidth() / 2, baseLineY + mTop, mTextPaint);
		if (mBottomText != null && !mBottomText.isEmpty()) {
			mTextPaint.setTextSize(mBottomTextSize);
			mTextPaint.setColor(mBottomTextColor);
			canvas.drawText(mBottomText, canvas.getWidth() / 2, baseLineY - mTop * 2 - mBottom * 2, mTextPaint);

		}
	}

	/**
	 * 设置点击样式或回复原状
	 * @param click     true 恢复， false 设置点击样式
	 */
	public void setClickedViewStyle(boolean click) {
		if (!click) {
			setBackgroundResource(0);
			//将非当月的信息设置为Bottom的颜色，与当月日期区别.
			if (mDate.getType() != Date.TYPE_THIS_MONTH && mChangeTopViewColor) {
				mTopTextColor = mDayItemAttrs.getTextColorBottom();
			} else {
				mTopTextColor = mDayItemAttrs.getTextColorTop();
			}
			mBottomTextColor = mDayItemAttrs.getTextColorBottom();
		} else {
			setBackground(mDayItemAttrs.getClickBg());
			mTopTextColor = mDayItemAttrs.getClickTextColor();
			mBottomTextColor = mDayItemAttrs.getClickTextColor();
		}
		postInvalidate();
	}


	public Date getDate() {
		return mDate;
	}
}
