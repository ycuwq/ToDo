package com.ycuwq.todo.di.module;

import android.arch.lifecycle.ViewModel;

import com.ycuwq.todo.AppViewModelFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * 为{@link AppViewModelFactory} 提供的ViewModel的实例Map。
 * Created by yangchen on 2017/11/30.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {
	Class<? extends ViewModel> value();
}
