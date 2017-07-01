package com.yangchen.extracalendarview.util;

import com.yangchen.extracalendarview.base.Date;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by 杨晨 on 2017/5/19.
 */
public class CalendarUtilTest {
	@Test
	public void getMonthDates() throws Exception {
		List<Date> dates = CalendarUtil.getMonthDates(2017, 5);
		assertEquals(35, dates.size());
		assertEquals(30, dates.get(0).getDay());
		assertEquals(1, dates.get(0).getWeek());
	}

	@Test
	public void getMonthRows() throws Exception {
		int row = CalendarUtil.getMonthRows(2017, 7);
		assertEquals(6, row);
	}

	@Test
	public void getWeekDates() throws Exception {
		List<Date> dates = CalendarUtil.getWeekDays(2017, 6, 0);
		assertEquals(28, dates.get(0).getDay());
	}

	@Test
	public void getDate() throws Exception {
		Date date = CalendarUtil.getDate(2017, 5, 19, Date.TYPE_THIS_MONTH);
		assertEquals(6, date.getWeek());
	}

}