package com.ycuwq.todo.base;

import android.arch.lifecycle.ViewModel;

import com.ycuwq.todo.utils.SnackbarText;

/**
 * Created by 杨晨 on 2017/10/18.
 */
public abstract class BaseViewModel extends ViewModel {

	public SnackbarText snackbarText = new SnackbarText();

	public String getSnackbarText() {
		return snackbarText.get();
	}
}
