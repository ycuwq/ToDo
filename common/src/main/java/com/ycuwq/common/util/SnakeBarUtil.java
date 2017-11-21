package com.ycuwq.common.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * SnakeBar的显示帮助类
 * Created by 杨晨 on 2017/5/13.
 */
public class SnakeBarUtil {

	public static void showSnackBar(View v, String snackBarText) {
		if (v == null || snackBarText == null) {
			return;
		}
		Snackbar.make(v, snackBarText, Snackbar.LENGTH_LONG).show();
	}
}
