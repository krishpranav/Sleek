package com.sleek.util;

public class Wrapper<T> {
    private T object;

    public Wrapper(T obj) {
        this.object = obj;
    }

    public T get() {
        return object;
    }

    public void set(T obj) {
        this.object = obj;
    }
}