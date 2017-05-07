package com.ycuwq.todo.common.util;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * 事件总线
 * Created by yangchen on 2017/2/21.
 */

public class RxBus {

    public RxBus() {

    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
    }

    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
