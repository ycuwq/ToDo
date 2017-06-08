package com.yangchen.extracalendarview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by 杨晨 on 2017/5/27.
 */
public class MonthView extends ViewPager {

	private int[] dateStart;//日历的开始年、月
	private int[] dateEnd;//日历的结束年、月
	private int[] dateInit;//默认展示、选中的日期（年、月、日）
	private boolean mShowLunar = true;//是否显示农历
	private boolean mShowHoliday = true;//是否显示节假日(不显示农历则节假日无法显示，节假日会覆盖农历显示)
	private boolean disableBefore = false;//是否禁用默认选中日期前的所有日期
	private int mTextColorTop = Color.BLACK;//阳历的日期颜色
	private int mTextColorBottom = Color.parseColor("#999999");//阴历的日期颜色
	private int mTextSizeTop = 14;//阳历日期文字尺寸
	private int mTextSizeBottom = 8;//阴历日期文字尺寸
	private MonthViewAdapter mMonthViewAdapter;
	private Context mContext;

	public MonthView(Context context) {
		this(context, null);
	}

	public MonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public void initAttr(boolean isShowLunar, boolean isShowHoliday, int textSizeTop, int textSizeBottom,
	                     int textColorTop, int textColorBottom) {
		mShowLunar = isShowLunar;
		mShowHoliday = isShowHoliday;
		mTextSizeTop = textSizeTop;
		mTextSizeBottom = textSizeBottom;
		mTextColorTop = textColorTop;
		mTextColorBottom = textColorBottom;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int calendarHeight;
		if (getAdapter() != null) {
			MonthView view = (MonthView) getChildAt(0);
			if (view != null) {
				calendarHeight = view.getMeasuredHeight();
				setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calendarHeight, MeasureSpec.EXACTLY));
			}
		}
	}



}
