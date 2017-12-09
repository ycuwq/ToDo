package com.ycuwq.common.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * 当FragmentDestroy时自动清理数值的引用
 * Created by 杨晨 on 2017/12/9.
 */
public class AutoClearedValue<T> {
	private T value;
	public AutoClearedValue(Fragment fragment, T value) {
		FragmentManager fragmentManager = fragment.getFragmentManager();
		fragmentManager.registerFragmentLifecycleCallbacks(
				new FragmentManager.FragmentLifecycleCallbacks() {
					@Override
					public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
						if (f == fragment) {
							AutoClearedValue.this.value = null;
							fragmentManager.unregisterFragmentLifecycleCallbacks(this);
						}
					}
				},false);
		this.value = value;
	}

	public T get() {
		return value;
	}
}
