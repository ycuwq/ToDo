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
	private final String TAG = getClass().getSimpleName();

	public InCalendarBottomBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
		return dependency instanceof ExtraCalendarView;
	}


	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
		//设置View的高度偏移量在dependency的下边
		child.setTranslationY(dependency.getBottom() - dependency.getTop());
		return true;
	}


}
