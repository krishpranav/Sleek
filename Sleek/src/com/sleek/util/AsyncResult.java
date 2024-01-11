package com.sleek.util;

public interface AsyncResult {
    public void onReady(V value, Throwable error);
}
