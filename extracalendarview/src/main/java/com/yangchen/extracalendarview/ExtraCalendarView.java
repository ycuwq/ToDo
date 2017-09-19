package com.yangchen.extracalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangchen.extracalendarview.base.Date;
import com.yangchen.extracalendarview.behavior.CalendarViewBehavior;
import com.yangchen.extracalendarview.listener.OnDayClickListener;
import com.yangchen.extracalendarview.listener.OnMonthChangeListener;
import com.yangchen.extracalendarview.util.Annotations;
import com.yangchen.extracalendarview.util.CalendarUtil;
import com.yangchen.extracalendarview.util.DensityUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 自定义的扩展日历
 * Created by yangchen on 2017/6/7.
 */
@SuppressWarnings("unused")
@CoordinatorLayout.DefaultBehavior(CalendarViewBehavior.class)
public class ExtraCalendarView extends LinearLayout {

	private final String TAG = getClass().getSimpleName();

	public final static int CALENDAR_TYPE_WEEK = 1;        //周模式
	public final static int CALENDAR_TYPE_MONTH = 2;       //月模式
	private @Annotations.CalendarType
	int mCalendarType = ExtraCalendarView.CALENDAR_TYPE_MONTH;        //显示的日历类型，显示一周，或者一月

	private @ColorInt
	int mBackgroundWeekInfo = Color.WHITE;    //日历的周信息背景颜色
	private @ColorInt
	int mTextColorTitle = Color.BLACK;        //标题的字体颜色
	private @ColorInt
	int mTextColorWeekInfo = Color.BLACK;
	private int mTextSizeWeekInfo = 14;                         //周信息字体大小
	private int mTextSizeTitle = 14;                            //标题字体大小
	private int mStartYear = 2017;      //日历开始显示的年份
	private int mStartMonth = 5;        //日历开始显示的月份

	private int mMonthCount = 1200;
	private DirectionButton mButtonPast;    //切换到上个月的按钮
	private DirectionButton mButtonFuture;  //切换到下个月的按钮
	private TextView mTitleTv;                //用来显示当前月份的
	private Drawable mLeftArrowMask = getResources().getDrawable(R.drawable.mcv_action_previous);
	private Drawable mRightArrowMask = getResources().getDrawable(R.drawable.mcv_action_next);
	private @ColorInt
	int mArrowColor = Color.BLACK;
	private Date mCurrentMonth;              //标记的当前显示的月份
	private Date mClickDate;        //当前选中的日期
	private DayView mClickedView;   //当前点击DayView

	private CalendarView mCalendarView;         //当前显示CalendarView
	private CalendarAdapter mCalendarAdapter;   //当前Calendar的Adapter
	private CalendarView mMonthCalendarView, mWeekCalendarView;   //CalendarView 的子类，显示月或周
	private MonthCalendarAdapter mMonthCalendarAdapter;
	private WeekCalendarAdapter mWeekCalendarAdapter;
	private FrameLayout mCalendarLayout;

	private DayItemAttrs mDayItemAttrs = new DayItemAttrs();
	private SimpleDateFormat monthDateFormat = new SimpleDateFormat("yyyy年MM月", Locale.SIMPLIFIED_CHINESE);
	private LinearLayout mTitleLayout;

	private OnDayClickListener mOnDayClickListener;
	private OnMonthChangeListener mOnMonthChangeListener;
	private WeekInfoView mWeekInfoView;

	private OnClickListener onClickListener = v -> {
		if (v == mButtonPast) {
			if (mCalendarView == null)
				return;
			mCalendarView.setCurrentItem(mCalendarView.getCurrentItem() - 1, true);
		} else if (v == mButtonFuture) {
			if (mCalendarView == null)
				return;
			mCalendarView.setCurrentItem(mCalendarView.getCurrentItem() + 1, true);
		}
	};

	private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			CalendarItemView itemView = mCalendarAdapter.getItem(position);
			// View没有绘制完成时，adapter得不到MonthItemView
			if (itemView != null) {
				mCurrentMonth = itemView.getCurrentMonth();
				if (mClickedView != null) {
					mClickedView.setClickedViewStyle(false);
					//切换Page后，将选择的日期变成当前页的日期。
					if (getCalendarType() == CALENDAR_TYPE_MONTH) {
						itemView.setClickView(new Date(mCurrentMonth.getYear(),
								mCurrentMonth.getMonth(), mClickDate.getDay()));
					} else {
						//周日期可直接用第几个View跳转。week的范围是1-7，所以减一
						itemView.setClickView(mClickDate.getWeek() - 1);
					}
				}
				if (mOnMonthChangeListener != null) {
					mOnMonthChangeListener.onChange(mCurrentMonth);
				}
			}
			updateTitleUI();
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	public ExtraCalendarView(Context context) {
		this(context, null);
	}

	public ExtraCalendarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ExtraCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setOrientation(VERTICAL);
		setClipToPadding(false);
		setClipChildren(false);
		Calendar calendar = Calendar.getInstance();
		long a = System.currentTimeMillis();
		initAttrs(attrs);
		setupChild();
		//Calendar中月是从第0个月算起的
		mClickDate = CalendarUtil.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH), Date.TYPE_THIS_MONTH);
		setCurrentMonth(mClickDate.getYear(), mClickDate.getMonth(), true);
		setTranslationZ(-2);
		Log.d(TAG, "ExtraCalendarView: " + (System.currentTimeMillis() - a));
		post(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "ExtraCalendarView: post" + (System.currentTimeMillis() - a));
			}
		});
	}

	private void initAttrs(AttributeSet attrs) {
		TypedArray a = getContext().getTheme()
				.obtainStyledAttributes(attrs, R.styleable.ExtraCalendarView, 0, 0);
		int count = a.getIndexCount();
		for (int i = 0; i < count; i++) {
			int attr = a.getIndex(i);

			if (attr == R.styleable.ExtraCalendarView_showHoliday) {
				mDayItemAttrs.setShowHoliday(a.getBoolean(attr, true));
			} else if (attr == R.styleable.ExtraCalendarView_showLunar) {
				mDayItemAttrs.setShowLunar(a.getBoolean(attr, true));
			} else if (attr == R.styleable.ExtraCalendarView_textColorTitle) {
				mTextColorTitle = a.getColor(attr, Color.BLACK);
			} else if (attr == R.styleable.ExtraCalendarView_textColorTop) {
				mDayItemAttrs.setTextColorTop(a.getColor(attr, Color.BLACK));
			} else if (attr == R.styleable.ExtraCalendarView_textColorTopBottom) {
				mDayItemAttrs.setTextColorBottom(a.getColor(attr, Color.parseColor("#999999")));
			} else if (attr == R.styleable.ExtraCalendarView_textColorWeekInfo) {
				mTextColorWeekInfo = a.getColor(attr, Color.BLACK);
			} else if (attr == R.styleable.ExtraCalendarView_textSizeBottom) {
				mDayItemAttrs.setTextSizeBottom(a.getInt(attr, 8));
			} else if (attr == R.styleable.ExtraCalendarView_textSizeTop) {
				mDayItemAttrs.setTextSizeTop(a.getInt(attr, 14));
			} else if (attr == R.styleable.ExtraCalendarView_textSizeTitle) {
				mTextSizeTitle = a.getInt(attr, 14);
			} else if (attr == R.styleable.ExtraCalendarView_textSizeWeekInfo) {
				mTextSizeWeekInfo = a.getInt(attr, 14);
			} else if (attr == R.styleable.ExtraCalendarView_backgroundColorMonth) {
				mDayItemAttrs.setBackgroundColor(a.getInt(attr, Color.WHITE));
			} else if (attr == R.styleable.ExtraCalendarView_backgroundColorWeekInfo) {
				mBackgroundWeekInfo = a.getInt(attr, Color.WHITE);
			} else if (attr == R.styleable.ExtraCalendarView_leftArrowMask) {
				Drawable mask = a.getDrawable(attr);
				setLeftArrowMask(mask);
			} else if (attr == R.styleable.ExtraCalendarView_rightArrowMask) {
				Drawable mask = a.getDrawable(attr);
				setRightArrowMask(mask);
			}
		}
		mDayItemAttrs.setClickBg(getResources().getDrawable(R.drawable.blue_circle));
	}

	private void setupChild() {
		//初始化头布局 箭头 文字
		mButtonPast = new DirectionButton(getContext());
		mButtonFuture = new DirectionButton(getContext());
		mButtonPast.setOnClickListener(onClickListener);
		mButtonFuture.setOnClickListener(onClickListener);
		mButtonPast.setImageDrawable(mLeftArrowMask);
		mButtonFuture.setImageDrawable(mRightArrowMask);
		setArrowColor(mArrowColor);
		mTitleTv = new TextView(getContext());
		mTitleTv.setTextColor(mTextColorTitle);
		mTitleTv.setTextSize(mTextSizeTitle);
		mTitleTv.setGravity(Gravity.CENTER);
		mTitleLayout = new LinearLayout(getContext());
		mTitleLayout.setOrientation(LinearLayout.HORIZONTAL);
		mTitleLayout.setClipChildren(false);
		mTitleLayout.setClipToPadding(false);
		mTitleLayout.setBackgroundColor(mBackgroundWeekInfo);
		mButtonPast.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		mTitleLayout.addView(mButtonPast, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

		mTitleTv.setGravity(Gravity.CENTER);
		mTitleLayout.addView(mTitleTv, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 5));

		mButtonFuture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		mTitleLayout.addView(mButtonFuture, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		mTitleLayout.setPadding(16, 24, 16, 24);
		addView(mTitleLayout, new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dp2px(getContext(), 48)));

		//周显示信息
		mWeekInfoView = new WeekInfoView(getContext());
		mWeekInfoView.setAttrs(mTextSizeWeekInfo, mTextColorWeekInfo, mBackgroundWeekInfo);
		addView(mWeekInfoView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		//日历主体，由MonthView和WeekView两部分组成，
		mCalendarLayout = new FrameLayout(getContext());
		//设置Z轴，其他View可以盖在该View上方，方便滚动View。
		mCalendarLayout.setZ(-1);
		mWeekCalendarView = new CalendarView(getContext());
		mMonthCalendarView = new CalendarView(getContext());
		mMonthCalendarAdapter = new MonthCalendarAdapter(this, mMonthCount, mStartYear, mStartMonth, mDayItemAttrs);
		mWeekCalendarAdapter = new WeekCalendarAdapter(this, mMonthCount, mStartYear, mStartMonth, mDayItemAttrs);
		mWeekCalendarView.setAdapter(mWeekCalendarAdapter);
		mMonthCalendarView.setAdapter(mMonthCalendarAdapter);
		mWeekCalendarView.setVisibility(INVISIBLE);
		mMonthCalendarView.addOnPageChangeListener(onPageChangeListener);
		mWeekCalendarView.addOnPageChangeListener(onPageChangeListener);
		mCalendarLayout.addView(mMonthCalendarView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mCalendarLayout.addView(mWeekCalendarView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		mCalendarView = mMonthCalendarView;
		mCalendarAdapter = mMonthCalendarAdapter;

		addView(mCalendarLayout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

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
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() == View.GONE) {
				continue;
			}
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			ViewGroup.LayoutParams p = child.getLayoutParams();

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

	private void updateTitleUI() {
		mTitleTv.setText(mCurrentMonth.getDate(monthDateFormat));
		mButtonPast.setEnabled(canGoBack());
		mButtonFuture.setEnabled(canGoForward());
	}

	public Date getClickDate() {
		return mClickDate;
	}

	/**
	 * 设置跳转到选定的月份， 如果超出范围则不会跳转
	 *
	 * @param year  选择的年
	 * @param month 选择的月
	 */
	public void setCurrentMonth(int year, int month) {
		setCurrentMonth(year, month, true);
	}

	/**
	 * 每个ViewPager的Item是相互独立的，如果写到CalendarItemView，mLastClickedView每个Item都可以有一个。
	 * 将点击事件的处理向上抛,用来保证保证全局只有一个点击效果，
	 */
	void onDateClicked(DayView dayView) {
		Date clickDate = dayView.getDate();
		int position = mCalendarView.getCurrentItem();

		//判断如果是点击当前月中的上月或下月的日期，则翻页
		//判断是否是日期显示是否是月模式，周模式不需要改变。
		if (mCalendarType == CALENDAR_TYPE_MONTH && clickDate.getType() == Date.TYPE_LAST_MONTH) {
			CalendarItemView itemView = mCalendarAdapter.getItem(position - 1);
			DayView tempDayView;
			//找到上月中的此日期的View，添加点击的效果
			if (itemView != null) {
				tempDayView = itemView.getDayView(dayView.getDate());
				if (tempDayView != null) {
					changeDayClickedAndStyle(tempDayView);
				}
			}
			onClickListener.onClick(mButtonPast);
		} else if (mCalendarType == CALENDAR_TYPE_MONTH && clickDate.getType() == Date.TYPE_NEXT_MONTH) {
			CalendarItemView itemView = mCalendarAdapter.getItem(position + 1);
			DayView tempDayView;
			if (itemView != null) {
				tempDayView = itemView.getDayView(dayView.getDate());
				if (tempDayView != null) {
					changeDayClickedAndStyle(tempDayView);
				}
			}
			onClickListener.onClick(mButtonFuture);
		} else {
			changeDayClickedAndStyle(dayView);
		}
		if (mOnDayClickListener != null) {
			mOnDayClickListener.onClick(dayView, mClickDate);
		}

	}

	public DayView getClickView() {
		return mClickedView;
	}


	/**
	 * 改变点击日期的样式，取消上次点击的样式，改变当前点击的日期
	 *
	 * @param dayView 返回的DayView
	 */
	void changeDayClickedAndStyle(DayView dayView) {
		if (dayView == null) {
			return;
		}
		if (mClickedView != null) {
			mClickedView.setClickedViewStyle(false);
		}
		dayView.setClickedViewStyle(true);
		mClickedView = dayView;
		mClickDate = dayView.getDate();
	}

	public void changeCalendarType() {
		//FIXME 切换成月模式速度太慢
		if (mCalendarType == CALENDAR_TYPE_MONTH) {
			mCalendarType = CALENDAR_TYPE_WEEK;
			mWeekCalendarView.setVisibility(VISIBLE);
			mMonthCalendarView.setVisibility(INVISIBLE);
			mCalendarView = mWeekCalendarView;
			mCalendarAdapter = mWeekCalendarAdapter;
			int weekPosition = CalendarUtil.getWeekPosition(mStartYear, mStartMonth, 1,
					mClickDate.getYear(), mClickDate.getMonth(), mClickDate.getDay());
			mCalendarView.setCurrentItem(weekPosition, false);
		} else {
			//FIXME 如果选中的是一个月的第一个星期，切换成星期会显示上个月。
			mCalendarType = CALENDAR_TYPE_MONTH;
			mMonthCalendarView.setVisibility(VISIBLE);
			mWeekCalendarView.setVisibility(INVISIBLE);
			mCalendarView = mMonthCalendarView;
			mCalendarAdapter = mMonthCalendarAdapter;

			int monthPosition = CalendarUtil.getMonthPosition(mStartYear, mStartMonth,
					mClickDate.getYear(), mClickDate.getMonth());
			mCalendarView.setCurrentItem(monthPosition, false);
		}
		mCalendarAdapter.setClickDate(mClickDate);
//		Log.d(TAG, "changeCalendarType: mMonthCalendarView: " + (mMonthCalendarView.getVisibility() == VISIBLE ? "VISIBLE" : "INVISIBLE"));
//		Log.d(TAG, "changeCalendarType:  mWeekCalendarView" + (mWeekCalendarView.getVisibility() == VISIBLE ? "VISIBLE" : "INVISIBLE"));

	}

	public int getCalendarHeight() {
		if (mCalendarType == CALENDAR_TYPE_MONTH) {
			return getHeight();
		} else {
			return mCalendarLayout.getTop() + mClickedView.getHeight();
		}
	}

	public void changeCalendarStyle() {
		//FIXME 切换成月模式速度太慢
		if (mCalendarType == CALENDAR_TYPE_MONTH) {
//			mCalendarType = CALENDAR_TYPE_WEEK;
			mWeekCalendarView.setVisibility(VISIBLE);
			mMonthCalendarView.setVisibility(INVISIBLE);
			mCalendarView = mWeekCalendarView;
			mCalendarAdapter = mWeekCalendarAdapter;
			int weekPosition = CalendarUtil.getWeekPosition(mStartYear, mStartMonth, 1,
					mClickDate.getYear(), mClickDate.getMonth(), mClickDate.getDay());
			mCalendarView.setCurrentItem(weekPosition, false);
		} else {
			//FIXME 如果选中的是一个月的第一个星期，切换成星期会显示上个月。
//			mCalendarType = CALENDAR_TYPE_MONTH;
			mMonthCalendarView.setVisibility(VISIBLE);
			mWeekCalendarView.setVisibility(INVISIBLE);
			mCalendarView = mMonthCalendarView;
			mCalendarAdapter = mMonthCalendarAdapter;

			int monthPosition = CalendarUtil.getMonthPosition(mStartYear, mStartMonth,
					mClickDate.getYear(), mClickDate.getMonth());
			mCalendarView.setCurrentItem(monthPosition, false);
		}
		mCalendarAdapter.setClickDate(mClickDate);
//		Log.d(TAG, "mMonthCalendarView: " + (mMonthCalendarView.getVisibility() == VISIBLE ? "VISIBLE" : "INVISIBLE"));
//		Log.d(TAG, "mWeekCalendarView:  " + (mWeekCalendarView.getVisibility() == VISIBLE ? "VISIBLE" : "INVISIBLE"));
	}


	public int getCalendarType() {
		return mCalendarType;
	}

	public CalendarView getCalendarView() {
		return mCalendarView;
	}

	public WeekInfoView getWeekInfoView() {
		return mWeekInfoView;
	}

	public void offsetCalendarView(int dy) {
		mCalendarView.offsetTopAndBottom(dy);
	}

	public void setCalendarType(@Annotations.CalendarType int calendarType) {
		mCalendarType = calendarType;
		//TODO 切换日历效果
//		mCalendarAdapter.setCalendarType(calendarType);
	}

	/**
	 * 跳转到选中日期页
	 *
	 * @param smoothScroll 是否平滑滚动
	 */
	public void setCurrentMonth(int year, int month, boolean smoothScroll) {
		int position = CalendarUtil.getMonthPosition(mStartYear, mStartMonth, year, month);
		if (position < 0 || position >= mMonthCount) {
			return;
		}
		//此处是防止View没有绘制完成时调用此方法，onPageChangeListener中adapter还没有加载页面，得不到日期数据
		mCurrentMonth = CalendarUtil.getDate(year, month, 1, Date.TYPE_THIS_MONTH);
		mCalendarView.setCurrentItem(position, smoothScroll);
		Log.d(TAG, "setCurrentMonth: ");
		invalidate();
	}

	public void setCurrentWeek(int year, int month, int day) {

	}

	/**
	 * 设置全部Title箭头的颜色
	 *
	 * @param color 颜色
	 */
	public void setArrowColor(@ColorInt int color) {
		if (color == 0) {
			return;
		}
		mArrowColor = color;
		mButtonPast.setColor(color);
		mButtonFuture.setColor(color);
		invalidate();
	}

	public void setLeftArrowMask(Drawable icon) {
		mLeftArrowMask = icon;
		mButtonPast.setImageDrawable(icon);
	}

	public void setRightArrowMask(Drawable icon) {
		mRightArrowMask = icon;
		mButtonFuture.setImageDrawable(icon);
	}

//	@Override
//	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		Log.d(TAG, "onLayout:");
//		final int parentLeft = getPaddingLeft();
//		final int parentWidth = r - l - getPaddingLeft() - getPaddingRight();
//		int childTop = getPaddingTop();
//		for (int i = 0; i < getChildCount(); i++) {
//			final View child = getChildAt(i);
//			if (child.getVisibility() == View.GONE) {
//				continue;
//			}
//			int width = child.getMeasuredWidth();
//			int height = child.getMeasuredHeight();
//
//			int delta = (parentWidth - width) / 2;
//			int childLeft = parentLeft + delta;
//			child.layout(childLeft, childTop, childLeft + width, childTop + height);
//			childTop += height;
//		}
//	}

	public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
		mOnDayClickListener = onDayClickListener;
	}

	public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener) {
		mOnMonthChangeListener = onMonthChangeListener;
	}

	public void setViewAnimation() {
		View child = getChildAt(1);
	}

	/**
	 * 设置标题栏是否显示
	 *
	 * @param isVisible true 显示， false 不显示
	 */
	public void setTitleVisible(boolean isVisible) {
		mTitleLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
	}

	/**
	 * 设置开始显示的月份
	 *
	 * @param startYear  开始年
	 * @param startMonth 开始月
	 * @param monthCount 一共显示的月份数量
	 */
	public void setStartDate(int startYear, int startMonth, int monthCount) {
		mStartYear = startYear;
		mStartMonth = startMonth;
		mMonthCount = monthCount;
		mCalendarAdapter.setStartDate(mStartYear, mStartMonth, mMonthCount);
	}

	public boolean canGoBack() {
		return mCalendarView.getCurrentItem() > 0;
	}

	public boolean canGoForward() {
		return mCalendarView.getCurrentItem() < (mCalendarAdapter.getCount() - 1);
	}

	public FrameLayout getCalendarLayout() {
		return mCalendarLayout;
	}
	public CalendarView getMonthCalendarView() {
		return mMonthCalendarView;
	}
	public CalendarView getWeekCalendarView() {
		return mWeekCalendarView;
	}
}
