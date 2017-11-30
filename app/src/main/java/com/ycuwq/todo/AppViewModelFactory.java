package com.ycuwq.todo;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * 自定义的ViewModelFactory
 * 由于所使用的ViewModel的构造方法中有参数，默认的{@code ViewModelProvider.Factory}无法实现，所以需要自定义。
 * 由Dagger实例化ViewModel,并且将ViewModel添加到Map。
 * Created by yangchen on 2017/11/30.
 */
@Singleton
public class AppViewModelFactory implements ViewModelProvider.Factory{

	private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

	@Inject
	public AppViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
		this.creators = creators;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		Provider<? extends ViewModel> creator = creators.get(modelClass);
		if (creator == null) {
			for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
				if (modelClass.isAssignableFrom(entry.getKey())) {
					creator = entry.getValue();
					break;
				}
			}
		}
		if (creator == null) {
			throw new IllegalArgumentException("unknown model class " + modelClass);
		}
		try {
			return (T) creator.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
