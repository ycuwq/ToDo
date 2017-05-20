package com.yangchen.extracalendarview.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 杨晨 on 2017/5/20.
 */
public class LunarUtilTest {
	@Test
	public void solarToLunar() throws Exception {
		String[] lunar = LunarUtil.solarToLunar(2017, 5, 30);
		assertEquals(lunar[0], "五月");
		assertEquals(lunar[1], "初五");
	}

}