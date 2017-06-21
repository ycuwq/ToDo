package com.yangchen.extracalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangchen.extracalendarview.util.CalendarUtil;
import com.yangchen.extracalendarview.util.DateUtil;
import com.yangchen.extracalendarview.util.DensityUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 自定义的扩展日历
 * Created by yangchen on 2017/6/7.
 */
public class ExtraCalendarView extends ViewGroup{

	private final String TAG = getClass().getSimpleName();

	private boolean mShowLunar = true;                          //是否显示农历
	private boolean mShowHoliday = true;                        //是否显示节假日(不显示农历则节假日无法显示，节假日会覆盖农历显示)
	private @ColorInt int mBackgroundWeekInfo = Color.WHITE;    //日历的周信息背景颜色
	private @ColorInt int mTextColorTitle = Color.BLACK;        //标题的字体颜色
	private @ColorInt int mTextColorWeekInfo = Color.BLACK;
	private int mTextSizeWeekInfo = 14;                         //周信息字体大小
	private int mTextSizeTitle = 14;                            //标题字体大小
	private int mStartYear = 2017;      //日历开始显示的年份
	private int mStartMonth = 5;        //日历开始显示的月份
	private MonthViewAdapter mMonthViewAdapter;
	private int mMonthCount = 12;
	private DirectionButton mButtonPast;    //切换到上个月的按钮
	private DirectionButton mButtonFuture;  //切换到下个月的按钮
	private TextView mTitleTv;                //用来显示当前月份的
	private Drawable mLeftArrowMask = getResources().getDrawable(R.drawable.mcv_action_previous);
	private Drawable mRightArrowMask = getResources().getDrawable(R.drawable.mcv_action_next);
	private @ColorInt int mArrowColor = Color.BLACK;
	private Date mCurrentMonth;              //标记的当前显示的月份
	private Date mClickDate;                    //当前选中的日期
	private MonthView mMonthView;
	private DayItemAttrs mDayItemAttrs = new DayItemAttrs();
	private SimpleDateFormat monthDateFormat = new SimpleDateFormat("yyyy年MM月", Locale.SIMPLIFIED_CHINESE);

	private OnClickListener onClickListener = v -> {
		if (v == mButtonPast) {
			if (mMonthView == null)
				return;
			mMonthView.setCurrentItem(mMonthView.getCurrentItem() - 1, true);
		} else if (v == mButtonFuture) {
			if (mMonthView == null)
				return;
			mMonthView.setCurrentItem(mMonthView.getCurrentItem() + 1, true);
		}
	};

	private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			//View没有绘制完成时，adapter得不到数据
			MonthItemView monthItemView = mMonthViewAdapter.getItem(position);
			if (monthItemView != null) {
				mCurrentMonth = monthItemView.getCurrentMonth();
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

		setClipToPadding(false);
		setClipChildren(false);
		Calendar calendar = Calendar.getInstance();

		initAttrs(attrs);
		setupChild();
		//Calendar中月是从第0个月算起的
		mClickDate = CalendarUtil.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH), Date.TYPE_THIS_MONTH);
		setCurrentMonth(mClickDate.getYear(), mClickDate.getMonth(), false);
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
				mDayItemAttrs.setTextColorTop(a.getColor(attr, Color.BLACK));
			} else if (attr == R.styleable.ExtraCalendarView_textColorTopBottom) {
				mDayItemAttrs.setTextColorBottom(a.getColor(attr, Color.parseColor("#999999")));
			} else if(attr == R.styleable.ExtraCalendarView_textColorWeekInfo) {
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
		//初始化头布局和箭头文字
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
		LinearLayout mTitleLayout = new LinearLayout(getContext());
		mTitleLayout.setOrientation(LinearLayout.HORIZONTAL);
		mTitleLayout.setClipChildren(false);
		mTitleLayout.setClipToPadding(false);

		mButtonPast.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		mTitleLayout.addView(mButtonPast, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

		mTitleTv.setGravity(Gravity.CENTER);
		mTitleLayout.addView(mTitleTv, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 5));

		mButtonFuture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		mTitleLayout.addView(mButtonFuture, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		mTitleLayout.setPadding(16,24,16,24);
		addView(mTitleLayout, new LayoutParams(DensityUtil.dp2px(getContext(), 48)));

		//周显示信息
		WeekView weekView = new WeekView(getContext());
		weekView.setAttrs(mTextSizeWeekInfo, mTextColorWeekInfo, mBackgroundWeekInfo);
		addView(weekView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		mMonthView = new MonthView(getContext());
		mMonthViewAdapter = new MonthViewAdapter(mMonthCount, mStartYear, mStartMonth, mDayItemAttrs);
		mMonthView.setAdapter(mMonthViewAdapter);
		mMonthView.addOnPageChangeListener(onPageChangeListener);
		addView(mMonthView);
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

	public void setCurrentMonth(int year, int month) {
		setCurrentMonth(year, month, true);
	}

	/**
	 *  跳转到选中日期页
	 * @param smoothScroll 是否平滑滚动
	 */
	public void setCurrentMonth(int year, int month , boolean smoothScroll) {
		int position = DateUtil.getBetweenDatePosition(mStartYear, mStartMonth, year, month);
		if (position < 0) {
			throw new RuntimeException("currentMonth not less than startMonth");
		}
		//此处是防止View没有绘制完成时调用此方法，onPageChangeListener中adapter还没有加载页面，得不到日期数据
		mCurrentMonth = CalendarUtil.getDate(year, month, 1, Date.TYPE_THIS_MONTH);
		mMonthView.setCurrentItem(position, smoothScroll);
	}

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
		mMonthViewAdapter.setStartDate(mStartYear, mStartMonth, mMonthCount);
	}

	public boolean canGoBack() {
		return mMonthView.getCurrentItem() > 0;
	}

	public boolean canGoForward() {
		return mMonthView.getCurrentItem() < (mMonthViewAdapter.getCount() - 1);
	}

	protected static class LayoutParams extends MarginLayoutParams {

		/**
		 * Create a layout that matches parent width, and is X number of tiles high
		 *
		 * @param tileHeight view height in number of tiles
		 */
		public LayoutParams(int tileHeight) {
			super(MATCH_PARENT, tileHeight);
		}

	}
}
