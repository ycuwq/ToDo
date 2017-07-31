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
	public void getMonthForOffset() throws Exception {
		Date date = CalendarUtil.getMonthForOffset(2017, 7, 23, -1);
		assertEquals(6, date.getMonth());
	}

	@Test
	public void getWeekForOffset() throws Exception {
		Date date = CalendarUtil.getWeekForOffset(2017, 7, 25, -1);
		assertEquals(18, date.getDay());
	}

	@Test
	public void getWeekDays() throws Exception {

		List<Date> dates = CalendarUtil.getWeekDays(2017, 8, 1);
		assertEquals(30, dates.get(0).getDay());
		assertEquals(5, dates.get(6).getDay());
		List<Date> dates2 = CalendarUtil.getWeekDays(2017, 7, 23);
		assertEquals(23, dates2.get(0).getDay());
		assertEquals(29, dates2.get(6).getDay());
		List<Date> dates3 = CalendarUtil.getWeekDays(2017, 7, 1);
		assertEquals(25, dates3.get(0).getDay());
		assertEquals(1, dates3.get(6).getDay());
	}

	@Test
	public void getWeekDaysForPosition() throws Exception {
		List<Date> dates = CalendarUtil.getWeekDaysForPosition(2017, 6, 1,4);
		assertEquals(25, dates.get(0).getDay());
	}

	@Test
	public void getWeekPosition() throws Exception {
		int weekPosition = CalendarUtil.getWeekPosition(2017, 7, 14, 2017, 8, 16);
		assertEquals(5, weekPosition);
	}

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
	public void getDate() throws Exception {
		Date date = CalendarUtil.getDate(2017, 5, 19, Date.TYPE_THIS_MONTH);
		assertEquals(6, date.getWeek());
	}
	@Test
	public void getBetweenDatePosition() throws Exception {
		int position = CalendarUtil.getMonthPosition(2017, 5, 2016, 5);
		assertEquals(-12, position);
	}

	@Test
	public void getDayForWeek() throws Exception {
		int day = CalendarUtil.getDayForWeek(2017, 7, 31);
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