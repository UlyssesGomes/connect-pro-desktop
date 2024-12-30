package com.connectpro.connectproserver.utils.designpatterns.observable;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> implements IObservable<T> {

    private String observableName = "DEFAULT_OBSERVABLE";

    protected List<Observer> observersList;
    private Observable observable;

    public Observable() {
        this.observersList = new ArrayList<>();
    }

    @Override
    public String getObservableName() {
        return observableName;
    }

    @Override
    public void attach(Observer observer) {
        observersList.add(observer);
    }

    @Override
    public void detach(Observer observer){
        observersList.remove(observer);
    }

    @Override
    public void notify(Subject<T> subjectValue){
        for(Observer o : observersList) {
            o.update(subjectValue);
        }
    }

    public void serObservableName(String observableName) {
        this.observableName = observableName;
    }
}
