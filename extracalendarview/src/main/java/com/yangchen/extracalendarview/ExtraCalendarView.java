package com.yangchen.extracalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yangchen on 2017/6/7.
 */
public class ExtraCalendarView extends ViewGroup{

	private boolean mShowLunar = true;//是否显示农历
	private boolean mShowHoliday = true;//是否显示节假日(不显示农历则节假日无法显示，节假日会覆盖农历显示)
	private boolean disableBefore = false;//是否禁用默认选中日期前的所有日期
	private @ColorInt int mTextColorTop = Color.BLACK;//阳历的日期颜色
	private @ColorInt int mTextColorBottom = Color.parseColor("#999999");//阴历的日期颜色
	private int mTextSizeTop = 14;//阳历日期文字尺寸
	private int mTextSizeBottom = 8;//阴历日期文字尺寸
	private int mStartYear = 2017;      //日历开始显示的年份
	private int mStartMonth = 5;        //日历开始显示的月份
	private @ColorInt int mBackgroundColor = Color.WHITE;
	private MonthViewAdapter mMonthViewAdapter;

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
		setupChild();
	}

	private void initAttrs(AttributeSet attrs) {
		TypedArray a = getContext().getTheme()
				.obtainStyledAttributes(attrs, R.styleable.ExtraCalendarView, 0,0);
	}

	private void setupChild() {
		WeekView weekView = new WeekView(getContext());
		weekView.setAttrs(mTextSizeTop, mTextColorTop, mBackgroundColor);
		addView(weekView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		MonthView monthView = new MonthView(getContext());
		mMonthViewAdapter = new MonthViewAdapter(mStartYear, mStartMonth, mShowHoliday, mShowLunar,
				mTextSizeTop, mTextSizeBottom, mTextColorTop, mTextColorBottom, mBackgroundColor);
		monthView.setAdapter(mMonthViewAdapter);
		addView(monthView);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
		int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
		int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

		int desiredWidth = specWidthSize - getPaddingLeft() - getPaddingRight();
		int desiredHeight = specHeightSize - getPaddingTop() - getPaddingBottom();

		final int childCount = getChildCount();
		int childHeightSum = 0;
		for (int i = 0; i < childCount; i ++) {
			final View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			LayoutParams p = child.getLayoutParams();

			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
					desiredWidth,
					MeasureSpec.EXACTLY
			);

			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
					child.getMeasuredHeight(),
					MeasureSpec.EXACTLY
			);
			childHeightSum += child.getMeasuredHeight();
			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
		}
		setMeasuredDimension(desiredWidth, childHeightSum);
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
