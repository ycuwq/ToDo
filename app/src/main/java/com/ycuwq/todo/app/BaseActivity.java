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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		removeSubscription();
	}

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
