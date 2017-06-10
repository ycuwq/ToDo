package com.yangchen.extracalendarview.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 杨晨 on 2017/5/20.
 */
public class SolarUtilTest {
	@Test
	public void getSolarHoliday() throws Exception {
		String holiday = SolarUtil.getSolarHoliday(2017, 5, 1);
		assertEquals(holiday, "劳动节");
	}

	@Test
	public void getMonthDays() throws Exception {
		int days = SolarUtil.getMonthDays(2017, 2);
		assertEquals(28, days);
		days = SolarUtil.getMonthDays(2020, 2);
		assertEquals(29, days);
	}

	@Test
	public void getFirstWeekOfMonth() throws Exception {
		int week = SolarUtil.getFirstWeekOfMonth(2017, 5);
		assertEquals(1, week);
	}

	@Test
	public void getCurrentDate() throws Exception {
		int[] date = SolarUtil.getCurrentDate();
		assertEquals(2017, date[0]);
		assertEquals(5, date[1]);

	}

}