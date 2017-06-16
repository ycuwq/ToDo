package com.yangchen.extracalendarview.util;

import java.util.Calendar;

/**
 * Calendar的工具类
 * Created by 杨晨 on 2017/5/14.
 */
public class DateUtil {
	/**
	 * 获取时期的星期几
	 */
	public static int getDayForWeek(int y, int m, int d) {
		Calendar calendar = Calendar.getInstance();
		//Month是从0开始算的，所以要-1
		calendar.set(y, m -1, d);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取是这个月的最大天数
	 */
	public static int getMaxDayForMonth(int y, int m) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(y, m - 1, 1);
		return calendar.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 获取第几个月
	 */
	public static int getMonthInYear(int y, int m) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(y, m - 1, 1);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取两个日期的月分之间的差
	 * @return 第二个 - 第一个
	 */
	public static int getBetweenDatePosition(int year1, int month1, int year2, int month2) {
		int year = year2 - year1;
		int month = month2 - month1;

		return year * 12 + month;
	}
}
