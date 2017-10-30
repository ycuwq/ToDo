package com.yangchen.extracalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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
import com.yangchen.extracalendarview.listener.OnDayViewClickListener;
import com.yangchen.extracalendarview.listener.OnMonthChangeListener;
import com.yangchen.extracalendarview.util.Annotations;
import com.yangchen.extracalendarview.util.CalendarUtil;
import com.yangchen.extracalendarview.util.DensityUtil;

import java.text.DateFormat;
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
	public static int weekCalendarHeight;
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
	private int mStartMonth = 1;        //日历开始显示的月份

	private int mCalendarViewCount = 1200;         //显示日期页的数量
	private DirectionButton mButtonPast;    //切换到上个月的按钮
	private DirectionButton mButtonFuture;  //切换到下个月的按钮
	private TextView mTitleTv;                //用来显示当前月份的
	private Drawable mLeftArrowMask = getResources().getDrawable(R.drawable.mcv_action_previous, null);
	private Drawable mRightArrowMask = getResources().getDrawable(R.drawable.mcv_action_next, null);
	private @ColorInt
	int mArrowColor = Color.BLACK;
	private Date mCurrentDate;        //当前选中的日期
	private DayView mClickedView;   //当前点击DayView

	private CalendarView mCalendarView;         //当前显示CalendarView
	private BaseCalendarAdapter mCalendarAdapter;   //当前Calendar的Adapter
	private CalendarView mMonthCalendarView, mWeekCalendarView;   //CalendarView 的子类，显示月或周
	private MonthCalendarAdapter mMonthCalendarAdapter;
	private WeekCalendarAdapter mWeekCalendarAdapter;
	private FrameLayout mCalendarLayout;

	private DayItemAttrs mDayItemAttrs = new DayItemAttrs();
	private DateFormat mMonthDateFormat = new SimpleDateFormat("yyyy年MM月", Locale.SIMPLIFIED_CHINESE);
	private LinearLayout mTitleLayout;

	private OnDayClickListener mOnDayClickListener;
	private OnMonthChangeListener mOnMonthChangeListener;
	private WeekInfoView mWeekInfoView;


	/**
	 * 为了配合左右滑动后将点击的日期切换到当月，所以在onPageChangeListener做了切换。
	 * 但是在切换周月的时候触发滑动会出现问题。所以设立flag禁用
	 */
	private boolean mPageSelectedNotClickedFlag = false;

	private OnClickListener mOnClickListener = v -> {
		if (v == mButtonPast) {
			if (mCalendarView == null) {
				return;
			}
			int position = mCalendarView.getCurrentItem() - 1;
			mCalendarView.setCurrentItem(position, true);
		} else if (v == mButtonFuture) {
			if (mCalendarView == null) {
				return;
			}
			int position = mCalendarView.getCurrentItem() + 1;
			mCalendarView.setCurrentItem(position, true);
		}
	};

	private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			if (!mPageSelectedNotClickedFlag) {
				setPageChangeClicked(position);
			}
			mPageSelectedNotClickedFlag = false;
			if (mOnMonthChangeListener != null) {
				mOnMonthChangeListener.onChange(mCurrentDate);
			}

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	private OnDayViewClickListener mOnDayViewClickListener = new OnDayViewClickListener() {
		@Override
		public void onDayViewClick(DayView dayView) {
			if (dayView == null) {
				return;
			}
			int position = mCalendarView.getCurrentItem();
			if (mOnDayClickListener != null) {
				if (!dayView.getDate().equals(mCurrentDate)) {
					mOnDayClickListener.onClick(mClickedView, dayView.getDate());
				}
			}
			mClickedView = dayView;
			mCurrentDate = dayView.getDate();
			//判断如果是点击当前月中的上月或下月的日期，则翻页
			//判断是否是日期显示是否是月模式，周模式不需要改变。
			if (mCalendarType == CALENDAR_TYPE_MONTH && dayView.getDate().getType() == Date.TYPE_LAST_MONTH) {

				BaseCalendarItemView itemView = mCalendarAdapter.getItem(position - 1);
				DayView tempDayView;
				//找到上月中的此日期的View，添加点击的效果
				if (itemView != null) {
					tempDayView = itemView.getDayView(dayView.getDate());
					if (tempDayView != null) {
						itemView.changeDayClickedAndStyle(tempDayView);
					}
				}
				mOnClickListener.onClick(mButtonPast);
			} else if (mCalendarType == CALENDAR_TYPE_MONTH && dayView.getDate().getType() == Date.TYPE_NEXT_MONTH) {
				BaseCalendarItemView itemView = mCalendarAdapter.getItem(position + 1);
				DayView tempDayView;
				if (itemView != null) {
					tempDayView = itemView.getDayView(dayView.getDate());
					if (tempDayView != null) {
						itemView.changeDayClickedAndStyle(tempDayView);
					}
				}
				mOnClickListener.onClick(mButtonFuture);
			}

			post(() -> {
				if (mClickedView != null) {
					weekCalendarHeight = mCalendarLayout.getTop() + mClickedView.getHeight();
				}
			});
			updateTitleUI();
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
		mDayItemAttrs.setClickBg(getResources().getDrawable(R.drawable.blue_circle, null));
		initAttrs(attrs);
		setupChild();

		setCurrentDateToToday();
		//设置日历的Z轴为负，以便RecyclerView等View可以覆盖到此View上方
		setTranslationZ(-2);

	}

	private void initAttrs(AttributeSet attrs) {
		TypedArray a = getContext().getTheme()
				.obtainStyledAttributes(attrs, R.styleable.ExtraCalendarView, 0, 0);
		int count = a.getIndexCount();
		for (int i = 0; i < count; i++) {
			int attr = a.getIndex(i);
			if (attr == R.styleable.ExtraCalendarView_calendarType) {
				mCalendarType = a.getInteger(attr, CALENDAR_TYPE_MONTH);
			} else if (attr == R.styleable.ExtraCalendarView_showHoliday) {
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
			} else if (attr == R.styleable.ExtraCalendarView_clickBackground) {
				Drawable drawable = a.getDrawable(attr);
				mDayItemAttrs.setClickBg(drawable);
			}
		}

	}

	private void setupChild() {
		//初始化头布局 箭头 文字
		mButtonPast = new DirectionButton(getContext());
		mButtonFuture = new DirectionButton(getContext());
		mButtonPast.setOnClickListener(mOnClickListener);
		mButtonFuture.setOnClickListener(mOnClickListener);
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
		mWeekCalendarView.setOffscreenPageLimit(1);
		mMonthCalendarView.setOffscreenPageLimit(1);
		mMonthCalendarAdapter = new MonthCalendarAdapter(mOnDayViewClickListener, mCalendarViewCount, mStartYear, mStartMonth, mDayItemAttrs);
		mWeekCalendarAdapter = new WeekCalendarAdapter(mOnDayViewClickListener, mCalendarViewCount, mStartYear, mStartMonth, mDayItemAttrs);
		mWeekCalendarView.setAdapter(mWeekCalendarAdapter);
		mMonthCalendarView.setAdapter(mMonthCalendarAdapter);

		mMonthCalendarView.addOnPageChangeListener(onPageChangeListener);
		mWeekCalendarView.addOnPageChangeListener(onPageChangeListener);
		mCalendarLayout.addView(mMonthCalendarView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mCalendarLayout.addView(mWeekCalendarView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		if (mCalendarType == CALENDAR_TYPE_MONTH) {
			mCalendarView = mMonthCalendarView;
			mCalendarAdapter = mMonthCalendarAdapter;
			mWeekCalendarView.setVisibility(INVISIBLE);
		} else {
			mMonthCalendarView.setVisibility(INVISIBLE);
			mCalendarView = mWeekCalendarView;
			mCalendarAdapter = mWeekCalendarAdapter;
		}

		addView(mCalendarLayout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

	}

	/**
	 * 设置当页面切换时，点击的view也切换到当前页
	 * @param position 当前page的position
	 */
	private void setPageChangeClicked(int position) {
		BaseCalendarItemView itemView = mCalendarAdapter.getItem(position);
		// View没有绘制完成时，adapter得不到MonthItemView
		if (itemView != null) {
			Date date = itemView.getCurrentMonth();
			if (mClickedView != null) {
				mClickedView.setClickedViewStyle(false);
				//切换Page后，将选择的日期变成当前页的日期。
				if (getCalendarType() == CALENDAR_TYPE_MONTH) {
					//在翻页的时候有可能会出现之前选中的日期超过当前月的最大天数，如果超过的话选择当前月的最后一天替代。
					if (mCurrentDate.getDay() > 28) {
						Calendar calendar = Calendar.getInstance();
						calendar.set(date.getYear(), date.getMonth() - 1, 1);
						int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
						if (maxDayOfMonth < mCurrentDate.getDay()) {
							itemView.setClickedView(new Date(date.getYear(),
									date.getMonth(), maxDayOfMonth));
						} else {
							itemView.setClickedView(new Date(date.getYear(),
									date.getMonth(), mCurrentDate.getDay()));
						}

					} else {
						itemView.setClickedView(new Date(date.getYear(),
								date.getMonth(), mCurrentDate.getDay()));
					}
				} else {
					//周日期可直接用第几个View跳转。week的范围是1-7，所以减一
					itemView.setClickedView(mCurrentDate.getWeek() - 1);
				}
			}
			if (mOnMonthChangeListener != null) {
				mOnMonthChangeListener.onChange(mCurrentDate);
			}
		}
	}

	public void setCurrentDateToToday() {
		Calendar calendar = Calendar.getInstance();

		//Calendar中月是从第0个月算起的
		Date currentDate = CalendarUtil.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH), Date.TYPE_THIS_MONTH);
		setCurrentDate(currentDate, false);
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
		mTitleTv.setText(mCurrentDate.getDate(mMonthDateFormat));
		mButtonPast.setEnabled(canGoBack());
		mButtonFuture.setEnabled(canGoForward());
	}

	public Date getClickDate() {
		return mCurrentDate;
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

	public DayView getClickView() {
		return mClickedView;
	}

	public void setCalendarType(@Annotations.CalendarType int calendarType) {
		mPageSelectedNotClickedFlag = true;
		mCalendarType = calendarType;
		if (mCalendarType == CALENDAR_TYPE_WEEK) {
			mCalendarView = mWeekCalendarView;
			mCalendarAdapter = mWeekCalendarAdapter;
			int weekPosition = CalendarUtil.getWeekPosition(mStartYear, mStartMonth, 1,
					mCurrentDate.getYear(), mCurrentDate.getMonth(), mCurrentDate.getDay());
			mCalendarView.setCurrentItem(weekPosition, false);
			mWeekCalendarView.setVisibility(VISIBLE);
			mMonthCalendarView.setVisibility(INVISIBLE);
		} else {

			mCalendarView = mMonthCalendarView;
			mCalendarAdapter = mMonthCalendarAdapter;

			int monthPosition = CalendarUtil.getMonthPosition(mStartYear, mStartMonth,
					mCurrentDate.getYear(), mCurrentDate.getMonth());
			mCalendarView.setCurrentItem(monthPosition, false);
			mMonthCalendarView.setVisibility(VISIBLE);
			mWeekCalendarView.setVisibility(INVISIBLE);
		}
		mCalendarAdapter.setClickedView(mCurrentDate);
		updateTitleUI();
		//正常来说，mPageSelectedNotClickedFlag只会一次生效。
		//当从周切换到月时，会调用该方法，这时会跳转到当前月。会触发onPageSelected，
		// 并在该方法内将mPageSelectedNotClickedFlag重置。
		// 但是当setCurrentItem的position没有发生改变时，不会触发onPageSelected，所以当这时翻页时，切换页面时将不会选中日期。
		post(() -> mPageSelectedNotClickedFlag = false);
	}


	public int getCalendarHeight() {
		if (mCalendarType == CALENDAR_TYPE_MONTH) {
			return getHeight();
		} else {
			if (mClickedView != null) {
				return mCalendarLayout.getTop() + mClickedView.getHeight();
			} else {
				return 0;
			}
		}
	}

	public void changeCalendarStyle() {
		mPageSelectedNotClickedFlag = true;
		if (mCalendarType == CALENDAR_TYPE_MONTH) {

			mCalendarView = mWeekCalendarView;
			mCalendarAdapter = mWeekCalendarAdapter;
			int weekPosition = CalendarUtil.getWeekPosition(mStartYear, mStartMonth, 1,
					mCurrentDate.getYear(), mCurrentDate.getMonth(), mCurrentDate.getDay());
			mCalendarView.setCurrentItem(weekPosition, false);
			mWeekCalendarView.setVisibility(VISIBLE);
			mMonthCalendarView.setVisibility(INVISIBLE);
		} else {
			mCalendarView = mMonthCalendarView;
			mCalendarAdapter = mMonthCalendarAdapter;

			int monthPosition = CalendarUtil.getMonthPosition(mStartYear, mStartMonth,
					mCurrentDate.getYear(), mCurrentDate.getMonth());
			mCalendarView.setCurrentItem(monthPosition, false);
			mMonthCalendarView.setVisibility(VISIBLE);
			mWeekCalendarView.setVisibility(INVISIBLE);
		}
		mCalendarAdapter.setClickedView(mCurrentDate);
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

	public void setCurrentDate(Date date, boolean smoothScroll) {
		if (date.equals(mCurrentDate)) {
			return;
		}
		mPageSelectedNotClickedFlag = true;

		int weekPosition = CalendarUtil.getWeekPosition(mStartYear, mStartMonth, 1,
				date.getYear(), date.getMonth(), date.getDay());
		int monthPosition = CalendarUtil.getMonthPosition(mStartYear, mStartMonth,
				date.getYear(), date.getMonth());
		if (weekPosition < 0 || weekPosition >= mCalendarViewCount) {
			return;
		}
		mWeekCalendarView.setCurrentItem(weekPosition, false);
		mMonthCalendarView.setCurrentItem(monthPosition, false);
//		mCalendarView.setCurrentItem(position, smoothScroll);
//		mCurrentDate = CalendarUtil.getDate(year, month, day, Date.TYPE_THIS_MONTH);
		post(() -> {
			mCalendarAdapter.setClickedView(date);
			updateTitleUI();
		});
		invalidate();
	}

	public void setCurrentDate(int year, int month, int day, boolean smoothScroll) {
		Date date = new Date(year, month, day);
		setCurrentDate(date, smoothScroll);
	}

	/**
	 * 跳转到选中日期页
	 *
	 * @param smoothScroll 是否平滑滚动
	 */
	public void setCurrentMonth(int year, int month, boolean smoothScroll) {
		int position = CalendarUtil.getMonthPosition(mStartYear, mStartMonth, year, month);
		if (position < 0 || position >= mCalendarViewCount) {
			return;
		}
		mCurrentDate = CalendarUtil.getDate(year, month, 1, Date.TYPE_THIS_MONTH);
		mCalendarView.setCurrentItem(position, smoothScroll);
		invalidate();
	}

	public void setCurrentWeek(int year, int month, int day) {
		int position = CalendarUtil.getWeekPosition(mStartYear, mStartMonth, 1, year, month, day);
		if (position < 0 || position >= mCalendarViewCount) {
			return;
		}
		mCurrentDate = CalendarUtil.getDate(year, month, day, Date.TYPE_THIS_MONTH);
		mCalendarView.setCurrentItem(position, false);
		invalidate();
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
	public void setClickedViewBackground(Drawable drawable) {
		mDayItemAttrs.setClickBg(drawable);
	}

	public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
		mOnDayClickListener = onDayClickListener;
	}

	public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener) {
		mOnMonthChangeListener = onMonthChangeListener;
	}

	public void setTitleDateFormat(DateFormat monthDateFormat) {
		this.mMonthDateFormat = monthDateFormat;
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
	 * 设置开始显示的日期
	 *
	 * @param startYear  开始年
	 * @param startMonth 开始月
	 * @param viewCount 一共显示的月份数量
	 */
	public void setStartDate(int startYear, int startMonth, int viewCount) {
		mStartYear = startYear;
		mStartMonth = startMonth;
		mCalendarViewCount = viewCount;
		mWeekCalendarAdapter.setStartDate(mStartYear, mStartMonth, mCalendarViewCount);
		mMonthCalendarAdapter.setStartDate(mStartYear, mStartMonth, mCalendarViewCount);
		Calendar calendar = Calendar.getInstance();
		mCurrentDate = CalendarUtil.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH), Date.TYPE_THIS_MONTH);
		setCurrentMonth(mCurrentDate.getYear(), mCurrentDate.getMonth(), true);
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
