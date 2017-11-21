package com.ycuwq.common.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by yangchen on 2017/11/21.
 */
public class Preconditions {
	public static @NonNull
	<T extends CharSequence> T checkStringNotEmpty(final T string) {
		if (TextUtils.isEmpty(string)) {
			throw new IllegalArgumentException();
		}
		return string;
	}

	public static @NonNull <T> T checkNotNull(final T reference) {
		if (reference == null) {
			throw new NullPointerException();
		}
		return reference;
	}
}
