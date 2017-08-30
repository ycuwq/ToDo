package com.yangchen.extracalendarview.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yangchen.extracalendarview.ExtraCalendarView;

import java.lang.ref.WeakReference;

/**
 * 让View 显示在{@link ExtraCalendarView} 的下面。
 * Created by yangchen on 2017/6/28.
 */
public class InCalendarBottomBehavior extends CoordinatorLayout.Behavior<View>{
	private final String TAG = getClass().getSimpleName();
	private int heardSize = -1;
	private WeakReference<View> dependentView;
	private boolean first = true;
	public InCalendarBottomBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
		if (dependency != null && dependency instanceof ExtraCalendarView) {
			dependentView = new WeakReference<>(dependency);
			return true;
		}
		return false;
	}

//	@Override
//	public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
//		parent.onLayoutChild(child, layoutDirection);
//		ExtraCalendarView extraCalendarView = (ExtraCalendarView) parent.getChildAt(0);
//		child.setTranslationY(extraCalendarView.getCalendarHeight());
//		return true;
//	}

	//
//	@Override
//	public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
//		parent.onLayoutChild(child, layoutDirection);
//		ExtraCalendarView extraCalendarView = (ExtraCalendarView) parent.getChildAt(0);
//		Log.d(TAG, "onLayoutChild: " + extraCalendarView.getCalendarType());
//		if (extraCalendarView.getCalendarType() == ExtraCalendarView.CALENDAR_TYPE_WEEK) {
//			Log.d(TAG, "onLayoutChild childTranslationY: " + child.getTranslationY());
//			child.setTranslationY(extraCalendarView.getClickView().getHeight() - extraCalendarView.getCalendarView().getHeight());
//		} else {
//			Log.d(TAG, "setMonthHeight: ");
//			child.setTranslationY(extraCalendarView.getHeight());
//		}
//		return true;
//	}
	//	@Override
//	public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
//		child.layout(0, 0, parent.getWidth(), (parent.getHeight() - dependentView.get().getHeight()) );
//		if (heardSize == -1) {
//			heardSize = dependentView.get().getHeight();
//			child.setTranslationY(heardSize);
//		}
//		return true;
//	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
		//设置View的高度偏移量在dependency的下边
//		int offset = dependency.getBottom() - dependency.getTop();
//		Log.d(TAG, "onDependentViewChanged offset: " + dependency.getHeight());
//		child.setTranslationY(dependency.getHeight());

		ExtraCalendarView extraCalendarView = (ExtraCalendarView) parent.getChildAt(0);
		child.setTranslationY(extraCalendarView.getCalendarHeight());
		Log.d(TAG, "onDependentViewChanged: " + extraCalendarView.getCalendarHeight());
		return true;
	}


}
