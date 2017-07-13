package com.yangchen.extracalendarview.util;

import android.support.annotation.IntDef;

import com.yangchen.extracalendarview.ExtraCalendarView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yangchen on 2017/7/13.
 */
public class Annotations {
	@IntDef({ExtraCalendarView.CALENDAR_TYPE_MONTH, ExtraCalendarView.CALENDAR_TYPE_WEEK})
	@Retention(RetentionPolicy.SOURCE)
	public @interface CalendarType {}
}
