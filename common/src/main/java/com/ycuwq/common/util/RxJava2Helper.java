package com.ycuwq.common.util;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava2的帮助类
 * Created by 杨晨 on 2017/5/9.
 */
public class RxJava2Helper {

	public static <T> ObservableTransformer<T, T> io2Main() {
		return observable -> observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
