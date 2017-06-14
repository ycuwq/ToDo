package com.yangchen.extracalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义的扩展日历
 * Created by yangchen on 2017/6/7.
 */
public class ExtraCalendarView extends ViewGroup{

	private boolean mShowLunar = true;                          //是否显示农历
	private boolean mShowHoliday = true;                        //是否显示节假日(不显示农历则节假日无法显示，节假日会覆盖农历显示)
	private @ColorInt int mBackgroundMonth = Color.WHITE;       //日历的背景颜色
	private @ColorInt int mBackgroundWeekInfo = Color.WHITE;    //日历的周信息背景颜色
	private @ColorInt int mTextColorTop = Color.BLACK;          //日历的日期颜色
	private @ColorInt int mTextColorBottom = Color.parseColor("#999999");   //节日，阴历的日期颜色
	private @ColorInt int mTextColorWeekInfo = Color.BLACK;     //日历的周信息字体颜色
	private @ColorInt int mTextColorTitle;    //标题字体颜色
	private int mTextSizeTop = 14;                              //日历的日期字体
	private int mTextSizeBottom = 8;                            //节日，阴历的字体
	private int mTextSizeWeekInfo = 14;                         //周信息字体大小
	private int mTextSizeTitle = 14;                            //标题字体大小
	private int mStartYear = 2017;      //日历开始显示的年份
	private int mStartMonth = 5;        //日历开始显示的月份
	private MonthViewAdapter mMonthViewAdapter;
	private int mMonthCount = 12;
	private DirectionButton mButtonPast;    //切换到上个月的按钮
	private DirectionButton mButtonFuture;  //切换到下个月的按钮
	private TextView mTitleTv;                //用来显示当前月份的
	private LinearLayout mTitleLayout;      //标题的父布局
	private Drawable mLeftArrowMask;
	private Drawable mRightArrowMask;

	public ExtraCalendarView(Context context) {
		this(context, null);
	}

	public ExtraCalendarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ExtraCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setClipToPadding(false);
		setClipChildren(false);

		mButtonPast = new DirectionButton(context);
		mButtonFuture = new DirectionButton(context);
		mTitleTv = new TextView(context);
		initAttrs(attrs);
		setupChild();
	}

	private void initAttrs(AttributeSet attrs) {
		TypedArray a = getContext().getTheme()
				.obtainStyledAttributes(attrs, R.styleable.ExtraCalendarView, 0,0);
		int count = a.getIndexCount();
		for (int i = 0; i < count; i++) {
			int attr = a.getIndex(i);

			if (attr == R.styleable.ExtraCalendarView_showHoliday) {
				mShowHoliday = a.getBoolean(attr, true);
			} else if (attr == R.styleable.ExtraCalendarView_showLunar) {
				mShowLunar = a.getBoolean(attr, true);
			} else if (attr == R.styleable.ExtraCalendarView_textColorTitle) {
				mTextColorTitle = a.getColor(attr, Color.BLACK);
			} else if (attr == R.styleable.ExtraCalendarView_textColorTop) {
				mTextColorTop = a.getColor(attr, Color.BLACK);
			} else if (attr == R.styleable.ExtraCalendarView_textColorTopBottom) {
				mTextColorBottom = a.getColor(attr, Color.parseColor("#999999"));
			} else if(attr == R.styleable.ExtraCalendarView_textColorWeekInfo) {
				mTextColorWeekInfo = a.getColor(attr, Color.BLACK);
			} else if (attr == R.styleable.ExtraCalendarView_textSizeBottom) {
				mTextSizeBottom = a.getInt(attr, 8);
			} else if (attr == R.styleable.ExtraCalendarView_textSizeTop) {
				mTextSizeTop = a.getInt(attr, 14);
			} else if (attr == R.styleable.ExtraCalendarView_textSizeTitle) {
				mTextSizeTitle = a.getInt(attr, 14);
			} else if (attr == R.styleable.ExtraCalendarView_textSizeWeekInfo) {
				mTextSizeWeekInfo = a.getInt(attr, 14);
			} else if (attr == R.styleable.ExtraCalendarView_backgroundColorMonth) {
				mBackgroundMonth = a.getInt(attr, Color.WHITE);
			} else if (attr == R.styleable.ExtraCalendarView_backgroundColorWeekInfo) {
				mBackgroundWeekInfo = a.getInt(attr, Color.WHITE);
			} else if (attr == R.styleable.ExtraCalendarView_leftArrowMask) {
				Drawable mask = a.getDrawable(attr);
				if (mask == null) {
					mask = getResources().getDrawable(R.drawable.mcv_action_previous);
				}
				setLeftArrowMask(mask);
			} else if (attr == R.styleable.ExtraCalendarView_rightArrowMask) {
				Drawable mask = a.getDrawable(attr);
				if (mask == null) {
					mask = getResources().getDrawable(R.drawable.mcv_action_next);
				}
				setRightArrowMask(mask);
			}
		}
	}

	private void setupChild() {
		mTitleLayout = new LinearLayout(getContext());
		mTitleLayout.setOrientation(LinearLayout.HORIZONTAL);
		mTitleLayout.setClipChildren(false);
		mTitleLayout.setClipToPadding(false);

		addView(mTitleLayout, new MarginLayoutParams(LayoutParams.MATCH_PARENT, 1));

		mButtonPast.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		mTitleLayout.addView(mButtonPast, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

		mTitleTv.setGravity(Gravity.CENTER);
		mTitleLayout.addView(mTitleTv, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 5));

		mButtonFuture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		mTitleLayout.addView(mButtonFuture, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

		WeekView weekView = new WeekView(getContext());
		weekView.setAttrs(mTextSizeWeekInfo, mTextColorWeekInfo, mBackgroundWeekInfo);
		addView(weekView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		MonthView monthView = new MonthView(getContext());
		mMonthViewAdapter = new MonthViewAdapter(mMonthCount, mStartYear, mStartMonth, mShowHoliday, mShowLunar,
				mTextSizeTop, mTextSizeBottom, mTextColorTop, mTextColorBottom, mBackgroundMonth);
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

	public void setLeftArrowMask(Drawable icon) {
		mLeftArrowMask = icon;
		mButtonPast.setImageDrawable(icon);
	}

	public void setRightArrowMask(Drawable icon) {
		mRightArrowMask = icon;
		mButtonFuture.setImageDrawable(icon);
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

	public void setStartDate(int startYear, int startMonth, int monthCount) {
		mStartYear = startYear;
		mStartMonth = startMonth;
		mMonthCount = monthCount;
	}
}
