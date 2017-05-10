package com.ycuwq.todo.app;

import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *
 * Created by 杨晨 on 2017/5/7.
 */

public class BaseActivity extends AppCompatActivity {

	private CompositeDisposable mDisposable;

	private App mApp;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		removeSubscription();
	}

	public App getApp() {
		if (mApp == null) {
			mApp = (App) getApplication();
		}

		return mApp;
	}

	/**
	 * RxJava2用来添加订阅事件流
	 * @param disposable
	 */
	public void addSubscription(Disposable disposable) {
		if (mDisposable == null) {
			mDisposable = new CompositeDisposable();
		}
		mDisposable.add(disposable);

	}

	public void removeSubscription() {
		if (mDisposable != null && mDisposable.size() > 0) {
			mDisposable.clear();
		}
	}
}
