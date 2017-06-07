package com.yangchen.extracalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yangchen on 2017/6/7.
 */
public class ExtraCalendarView extends ViewGroup{
	public ExtraCalendarView(Context context) {
		this(context, null);
	}

	public ExtraCalendarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ExtraCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
			//If we're on good Android versions, turn off clipping for cool effects
		setClipToPadding(false);
		setClipChildren(false);
		initAttrs(attrs);
	}

	private void initAttrs(AttributeSet attrs) {
		TypedArray a = getContext().getTheme()
				.obtainStyledAttributes(attrs, R.styleable.ExtraCalendarView, 0,0);
	}

	private void setupChild() {

	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
		int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
		int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

		int desiredWidth = specWidthSize - getPaddingLeft() - getPaddingRight();
		int desiredHight = specHeightSize - getPaddingLeft() - getPaddingRight();


	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int parentLeft = getPaddingLeft();
		final int parentWidth = r - l - getPaddingLeft() - getPaddingRight();
		int childTop = getPaddingTop();
		for (int i = 0; i < getChildCount(); i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() == View.GONE) {
				continue;
			}
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();

			int delta = (parentWidth - width) / 2;
			int childLeft = parentLeft + delta;
			child.layout(childLeft, childTop, childLeft + width, childTop + height);
			childTop += height;
		}
	}
}
