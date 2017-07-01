package com.yangchen.extracalendarview.util;

import com.yangchen.extracalendarview.base.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * Created by 杨晨 on 2017/5/19.
 */
public class CalendarUtil {

	public static Date getDate(int year, int month, int day, int type) {
		Date date = new Date(year, month, day);
		date.setHoliday(SolarUtil.getSolarHoliday(year, month, day));
		date.setType(type);
		String[] lunar = LunarUtil.solarToLunar(year, month, day);
		date.setLunarMonth(lunar[0]);
		date.setLunarDay(lunar[1]);
		date.setLunarHoliday(lunar[2]);
		date.setWeek(DateUtil.getDayForWeek(year, month, day));
		return date;
	}

	/**
	 * 获取一个月的日期数据
	 * @param year
	 * @param month
	 * @return
	 */
	public static List<Date> getMonthDates(int year, int month) {
		List<Date> dates = new ArrayList<>();
		int week = SolarUtil.getFirstWeekOfMonth(year, month);
		int lastYear, lastMonth, nextYear, nextMonth;

		//获取上个月
		if (month == 1) {
			//判断是否是第一个月last = 去年最后一个月
			lastMonth = 12;
			lastYear = year - 1;
		}  else {
			lastMonth = month - 1;
			lastYear = year;
		}

		if (month == 12) {
			nextMonth = 1;
			nextYear =  year + 1;
		} else {
			nextMonth = month + 1;
			nextYear = year;
		}

		int lastMonthDay = DateUtil.getMaxDayForMonth(lastYear, lastMonth);
		int thisMonthDay = DateUtil.getMaxDayForMonth(year, month);
		//加入在当前月第一个星期的上个月的日期。
		for (int i = 0; i < week; i++) {
			Date date = getDate(lastYear, lastMonth, lastMonthDay - week + 1 + i, Date.TYPE_LAST_MONTH);
			dates.add(date);
		}
		for (int i = 0; i < thisMonthDay; i++) {
			Date date = getDate(year, month, i + 1, Date.TYPE_THIS_MONTH);
			dates.add(date);
		}
		for (int i = 0; i < 7 * getMonthRows(year, month) - thisMonthDay - week; i++) {
			Date date = getDate(nextYear, nextMonth, i + 1, Date.TYPE_NEXT_MONTH);
			dates.add(date);
		}
		return dates;
	}

	/**
	 * 获取距离开始日期 + 周数的一周的日期。
	 * @param startYear     开始年
	 * @param startMonth    开始月
	 * @param positionWeek  偏移周数
	 * @return  返回一周的日期，第一天是周日
	 */
	public static List<Date> getWeekDays(int startYear, int startMonth, int positionWeek) {
		ArrayList<Date> dates = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.set(startYear, startMonth - 1, 1);

		//将日期设置为开始日期 + 距离周数的日期。
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week + positionWeek);

		int currentMonth = calendar.get(Calendar.MONTH);
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < 7; i++) {
			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear + i);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DATE);
			int type;
			//月的类型，当月、上月、下月
			if (currentMonth > month) {
				type = Date.TYPE_LAST_MONTH;
			} else if (currentMonth < month) {
				type = Date.TYPE_NEXT_MONTH;
			} else {
				type = Date.TYPE_THIS_MONTH;
			}
			dates.add(getDate(year, month + 1, day, type));
		}
		return dates;
	}

	/**
	 * 计算当前月需要显示几行
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthRows(int year, int month) {
		int items = SolarUtil.getFirstWeekOfMonth(year, month) + SolarUtil.getMonthDays(year, month);
		int rows = items % 7 == 0 ? items / 7 : (items / 7) + 1;
		if (rows == 4) {
			rows = 5;
		}
		return rows;
	}

	/**
	 * 根据ViewPager position 得到对应年月
	 * @param position  开始年月的延后月份
	 */
	public static int[] positionToDate(int position, int startY, int startM) {
		int year = position / 12 + startY;
		int month = position % 12 + startM;

		if (month > 12) {
			month = month % 12;
			year = year + 1;
		}
		return new int[]{year, month};
	}


}
