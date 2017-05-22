package com.yangchen.extracalendarview.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 杨晨 on 2017/5/14.
 */
public class DateUtilTest {
	@Test
	public void getDayForWeek() throws Exception {
		int day = DateUtil.getDayForWeek(2017, 5, 14);
		assertEquals(1, day);
	}

	@Test
	public void getMaxDayForMonth() throws Exception {
		int day = DateUtil.getMaxDayForMonth(2017, 5);
		assertEquals(31, day);
	}

	@Test
	public void getMonthInYear() throws Exception {
		int month = DateUtil.getMonthInYear(2017, 5);
		assertEquals(5, month);
	}


}