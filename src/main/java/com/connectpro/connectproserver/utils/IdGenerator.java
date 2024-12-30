package com.connectpro.connectproserver.utils;

public class IdGenerator {
    private static int id = 0;

    public synchronized static int generateId(){
        return id++;
    }
}
