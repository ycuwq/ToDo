package com.yangchen.extracalendarview.util;

import com.yangchen.extracalendarview.Date;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 杨晨 on 2017/5/19.
 */
public class CalendarUtilTest {
	@Test
	public void getDate() throws Exception {
		Date date = CalendarUtil.getDate(2017, 5, 19);
		assertEquals(6, date.getWeek());
	}

}