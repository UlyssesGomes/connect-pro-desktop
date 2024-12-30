package com.connectpro.connectproserver.utils.designpatterns.observable;

public interface Observer<T> {
    public void update(Subject<T> subjectValue);
}
