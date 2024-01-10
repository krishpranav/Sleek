package com.sleek.util;

abstract class AbstractStringBuilder {
    static final int INITIAL_CAPACITY = 16;

    private char[] value;

    private int count;

    final char[] getValue() {
        return value;
    }

    final void set(char[] val, int len) {
        if (val == null) {
            val = new char[0];
        }
        if (val.length < len) {
            throw new RuntimeException();
        }

        value = val;
        count = len;
    }

    AbstractStringBuilder() {
        value = new char[INITIAL_CAPACITY];
    }
}
