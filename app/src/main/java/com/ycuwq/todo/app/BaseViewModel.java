package com.ycuwq.todo.app;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

/**
 * Created by 杨晨 on 2017/10/18.
 */
public class BaseViewModel extends ViewModel {

	public ObservableField<String> snakeBarText = new ObservableField<>();

}
