package com.yangchen.extracalendarview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by 杨晨 on 2017/5/27.
 */
public class ExtraCalendarView extends ViewPager {

	private int[] dateStart;//日历的开始年、月
	private int[] dateEnd;//日历的结束年、月
	private int[] dateInit;//默认展示、选中的日期（年、月、日）
	private boolean showLastNext = true;//是否显示上个月、下个月
	private boolean showLunar = true;//是否显示农历
	private boolean showHoliday = true;//是否显示节假日(不显示农历则节假日无法显示，节假日会覆盖农历显示)
	private boolean showTerm = true;//是否显示节气
	private boolean disableBefore = false;//是否禁用默认选中日期前的所有日期
	private boolean switchChoose = true;//单选时切换月份，是否选中上次的日期
	private int colorSolar = Color.BLACK;//阳历的日期颜色
	private int colorLunar = Color.parseColor("#999999");//阴历的日期颜色
	private int colorHoliday = Color.parseColor("#EC9729");//节假日的颜色
	private int colorChoose = Color.WHITE;//选中的日期文字颜色
	private int sizeSolar = 14;//阳历日期文字尺寸
	private int sizeLunar = 8;//阴历日期文字尺寸

	public ExtraCalendarView(Context context) {
		super(context);
	}

	public ExtraCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	private void initAttr(AttributeSet attrs) {

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}



}
