package com.ycuwq.todo.base;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;

import com.ycuwq.todo.App;

/**
 *
 * Created by 杨晨 on 2017/5/7.
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

	private App mApp;

	public App getApp() {
		if (mApp == null) {
			mApp = (App) getApplication();
		}

		return mApp;
	}

}
