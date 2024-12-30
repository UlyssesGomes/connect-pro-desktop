package com.connectpro.connectproserver.utils.designpatterns.observable;

public interface IObservable<T> {
    public String getObservableName();
    public void attach(Observer observer);
    public void detach(Observer observer);
    public void notify(Subject<T> subjectValue);
}
