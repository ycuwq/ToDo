package com.yangchen.extracalendarview;

/**
 * Created by 杨晨 on 2017/5/18.
 */
public class Date {
	private int year;
	private int month;
	private int day;
	private int week;
	private String holiday;         //节假日

	private String lunarMonth;      //农历月
	private String lunarDay;        //农历日
	private String lunarHoliday;     //农历节日

	public Date() {
	}

	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public String getLunarMonth() {
		return lunarMonth;
	}

	public void setLunarMonth(String lunarMonth) {
		this.lunarMonth = lunarMonth;
	}

	public String getLunarDay() {
		return lunarDay;
	}

	public void setLunarDay(String lunarDay) {
		this.lunarDay = lunarDay;
	}

	public String getLunarHoliday() {
		return lunarHoliday;
	}

	public void setLunarHoliday(String lunarHoliday) {
		this.lunarHoliday = lunarHoliday;
	}
}
