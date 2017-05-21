package com.yangchen.extracalendarview.util;

import com.yangchen.extracalendarview.Date;

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
}
