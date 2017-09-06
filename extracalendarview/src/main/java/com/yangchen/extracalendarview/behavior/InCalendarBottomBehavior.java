package com.yangchen.extracalendarview.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yangchen.extracalendarview.ExtraCalendarView;

/**
 * 让View 显示在{@link ExtraCalendarView} 的下面。
 * Created by yangchen on 2017/6/28.
 */
public class InCalendarBottomBehavior extends CoordinatorLayout.Behavior<View>{

	public InCalendarBottomBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
		parent.onLayoutChild(child, layoutDirection);
		ExtraCalendarView extraCalendarView = (ExtraCalendarView) parent.getChildAt(0);
		extraCalendarView.post(() -> {
			child.setY(extraCalendarView.getCalendarHeight());
		});
		return true;
	}

}
