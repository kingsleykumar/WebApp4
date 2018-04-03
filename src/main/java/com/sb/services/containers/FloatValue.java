package com.sb.services.containers;

/**
 * Created by Kingsley Kumar on 18/09/2016.
 */
public class FloatValue {

    float value;

    public FloatValue(float value) {

        this.value = value;
    }

    public void add(float value) {

        this.value += value;
    }

    public float get() {

        return value;
    }
}
