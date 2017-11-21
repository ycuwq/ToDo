package com.ycuwq.todo;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 杨晨 on 2017/5/12.
 */
@Module
public class ApplicationModule {

	public Context mContext;

	public ApplicationModule(Context context) {
		mContext = context;
	}

	@Provides
	Context provideContext() {
		return mContext;
	}
}
