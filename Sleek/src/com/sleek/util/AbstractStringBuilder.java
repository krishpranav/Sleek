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

    AbstractStringBuilder(int capacity) {
        if (capacity < 0) {
            throw new NegativeArraySizeException();
        }

        value = new char[capacity];
    }

    AbstractStringBuilder(String string) {
        count = string.length();
        value = new char[count + INITIAL_CAPACITY];
        string.getChars(0, count, value, 0);
    }

    private void enlargeBuffer(int min) {
        int newSize = ((value.length >> 1) + value.length) + 2;
        char[] newData = new char[min > newSize ? min : newSize];
        System.arraycopy(value, 0, newData, 0, count);
        value = newData;
    }

    final void appendNull() {
        int newSize = count + 4;

    }

    final void append0(String string) {
        if (string == null) {
            appendNull();
            return;
        }

        int adding = string.length();
        int newSize = count + adding;

        if (newSize > value.length) {
            enlargeBuffer(newSize);
        }

        string.getChars(0, adding, value, count);
        count = newSize;
    }

    public int capacity() {
        return value.length;
    }
}
