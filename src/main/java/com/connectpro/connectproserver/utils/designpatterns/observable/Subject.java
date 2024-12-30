package com.connectpro.connectproserver.utils.designpatterns.observable;

public class Subject<T> {
    public String subjectName;
    public T data;

    public Subject(String subjectName, T data) {
        this.subjectName = subjectName;
        this.data = data;
    }
}
