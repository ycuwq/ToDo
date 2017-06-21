package com.yangchen.extracalendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义扩展的CalendarView
 * Created by 杨晨 on 2017/5/11.
 */
public class MonthItemView extends ViewGroup {

	private static final int MAX_ROW = 6;       //最大显示的行数
	private static final int COLUMN = 7;        //显示的列数

	private int mCurrentMonthDay, mLastMonthDay, mNextMonthDay; //当前月份天数，上月天数，下月天数。
	private Date mCurrentMonth;
	private Date mCurrentDay;

	private DayItemAttrs mDayItemAttrs;
	private com.yangchen.extracalendarview.OnClickListener onClickListener;
	private View mLastClickedView;              //上次点击过的View
	public MonthItemView(@NonNull Context context) {
		this(context, null);
	}
	public MonthItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MonthItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void initAttr(DayItemAttrs dayItemAttrs) {
		mDayItemAttrs = dayItemAttrs;
		super.setBackgroundColor(mDayItemAttrs.getBackgroundColor());
	}

	public void setDates(List<Date> dates, int currentMonthDays) {
		if (dates.size() > 0) {
			removeAllViews();
		}
		mCurrentMonthDay = currentMonthDays;
		mLastMonthDay = 0;
		mNextMonthDay = 0;
		for (int i = 0; i < dates.size(); i ++) {
			Date date = dates.get(i);
			switch (date.getType()) {
				case Date.TYPE_LAST_MONTH:
					mLastMonthDay++;
					addView(new View(getContext()), i);
					continue;
				case Date.TYPE_NEXT_MONTH:
					addView(new View(getContext()), i);
					continue;
				default:
					break;
			}
			View view = LayoutInflater.from(getContext()).inflate(R.layout.item_month_layout, null);
			TextView topTv = (TextView) view.findViewById(R.id.tv_item_month_top);
			TextView bottomTv = (TextView) view.findViewById(R.id.tv_item_month_bottom);
			topTv.setTextColor(mDayItemAttrs.getTextColorTop());
			bottomTv.setTextColor(mDayItemAttrs.getTextColorBottom());
			topTv.setTextSize(mDayItemAttrs.getTextSizeTop());
			bottomTv.setTextSize(mDayItemAttrs.getTextSizeBottom());
			topTv.setText(String.valueOf(date.getDay()));
			//设置农历数据
			if (mDayItemAttrs.isShowLunar()) {
				bottomTv.setText(date.getLunarDay());
			}
			//设置节日数据
			if (mDayItemAttrs.isShowHoliday()) {
				if (!TextUtils.isEmpty(date.getLunarHoliday())) {
					bottomTv.setText(date.getLunarHoliday());
				}
				if (!TextUtils.isEmpty(date.getHoliday())) {
					bottomTv.setText(date.getHoliday());
				}
			}
			view.setOnClickListener(v -> {
				//TODO 由于ViewPager的复用策略，翻两页之后再回来，点击效果消失。
				mCurrentDay = date;
				if (mLastClickedView != null) {
					setClickedViewStyle(mLastClickedView, true);
				}
				setClickedViewStyle(view, false);
				mLastClickedView = view;
				if (onClickListener != null) {
					onClickListener.onClick(view, date);
				}
			});
			addView(view);
		}
		mCurrentMonth = dates.get(mLastMonthDay);
		requestLayout();
	}

	public void setChildClicked() {
	}

	/**
	 * 设置点击样式或回复原状
	 * @param view      选定的view
	 * @param reset     true 恢复， false 设置点击样式
	 */
	private void setClickedViewStyle(View view, boolean reset) {
		TextView topTv = (TextView) view.findViewById(R.id.tv_item_month_top);
		TextView bottomTv = (TextView) view.findViewById(R.id.tv_item_month_bottom);

		if (reset) {
			view.setBackgroundResource(0);
			topTv.setTextColor(mDayItemAttrs.getTextColorTop());
			bottomTv.setTextColor(mDayItemAttrs.getTextColorBottom());
		} else {
			view.setBackground(mDayItemAttrs.getClickBg());
			topTv.setTextColor(mDayItemAttrs.getClickTextColor());
			bottomTv.setTextColor(mDayItemAttrs.getClickTextColor());
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (getChildCount() == 0) {
			return;
		}

		View childView = getChildAt(0);
		int itemWidth = childView.getMeasuredWidth();
		int itemHeight = childView.getMeasuredHeight();

		//当显示五行时扩大行间距
		int dy = 0;
		if (getChildCount() == 35) {
			dy = itemWidth / 5;
		}

		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			int left = i % COLUMN * itemWidth;// + (2 * (i % COLUMN) + 1) * 7
			int top = i / COLUMN * (itemHeight + dy);
			int right = left + itemWidth;
			int bottom = top + itemHeight;
			view.layout(left, top, right, bottom);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

		int itemWidth = widthSpecSize / COLUMN;// - 14

		int width = widthSpecSize;
		int height = itemWidth * MAX_ROW;

		setMeasuredDimension(width, height);

		int itemHeight;

//        if (getChildCount() == 35) {
//	        itemHeight = getMeasuredHeight() / 5;
//        } else {
			itemHeight = itemWidth;
//        }

		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			childView.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
		}
	}

	public void setOnClickListener(com.yangchen.extracalendarview.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	public Date getCurrentMonth() {
		return mCurrentMonth;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
