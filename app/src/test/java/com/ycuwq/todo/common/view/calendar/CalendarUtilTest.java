package com.ycuwq.todo.common.view.calendar;

import com.yangchen.extracalendarview.CalendarUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 杨晨 on 2017/5/14.
 */
public class CalendarUtilTest {
	@Test
	public void getDayForWeek() throws Exception {
		int day = CalendarUtil.getDayForWeek(2017, 5, 14);
		assertEquals(1, day);
	}

	@Test
	public void getMaxDayForMonth() throws Exception {
		int day = CalendarUtil.getMaxDayForMonth(2017, 5);
		assertEquals(31, day);
	}

	@Test
	public void getMonthInYear() throws Exception {
		int month = CalendarUtil.getMonthInYear(2017, 5);
		assertEquals(5, month);
	}

}