package com.yangchen.extracalendarview.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yangchen.extracalendarview.ExtraCalendarView;

import static android.content.ContentValues.TAG;

/**
 * 让View 显示在{@link ExtraCalendarView} 的下面。
 * Created by yangchen on 2017/6/28.
 */
public class InCalendarBottomBehavior extends CoordinatorLayout.Behavior<View>{


	public InCalendarBottomBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
		return dependency instanceof ExtraCalendarView;
	}

	/**
	 *  Fixme 当页面刚加载完成时此方法不会被调用，临时在ExtraCalendarView绘制完成后改变下位置解决
	 */
	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
		Log.d(TAG, "onDependentViewChanged: ");
		ViewCompat.offsetTopAndBottom(child, dependency.getBottom() - dependency.getTop());
		return true;
	}

}
