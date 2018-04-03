package com.sb.services.containers;

/**
 * Created by Kingsley Kumar on 30/10/2016.
 */
public class IntegerValue {

    int value;

    public IntegerValue(int value) {

        this.value = value;
    }

    public void add(int value) {

        this.value += value;
    }

    public int get() {

        return value;
    }
}
