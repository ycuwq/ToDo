package com.yangchen.extracalendarview.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 杨晨 on 2017/5/20.
 */
public class SolarUtilTest {
	@Test
	public void getSolarHoliday() throws Exception {
		String holiday = SolarUtil.getSolarHoliday(2017, 1, 1);
		assertEquals(holiday, "元旦");
	}

	@Test
	public void chingMingDay() throws Exception {

	}

	@Test
	public void getMonthDays() throws Exception {
	}

	@Test
	public void getFirstWeekOfMonth() throws Exception {
	}

	@Test
	public void getCurrentDate() throws Exception {
	}

}