package com.connectpro.connectproserver.utils.observableimplementations;

import javafx.scene.text.Text;

public class ObservableData<T> {
    public int id;
    public T data;

    public ObservableData(int id,T data){
        this.id = id;
        this.data = data;
    }

    public ObservableData() {
        id = Integer.MIN_VALUE;
        data = null;
    }
}
