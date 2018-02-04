package com.ycuwq.todo.utils;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * 仿照{@link android.databinding.ObservableField}实现的监听。
 * 取消掉设置重复值的判断，因为Snackbar中可能会出现两次弹出的信息是重复的情况，
 * Created by ycuwq on 2018/2/4.
 */
public class SnackbarText extends BaseObservable implements Serializable {

    static final long serialVersionUID = 1L;
    private String mValue;

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    public SnackbarText(String value) {
        mValue = value;
    }

    /**
     * Creates an empty observable object
     */
    public SnackbarText() {
    }

    /**
     * @return the stored value.
     */
    public String get() {
        return mValue;
    }

    /**
     * Set the stored value.
     */
    public void set(String value) {
        mValue = value;
        notifyChange();

    }

}
